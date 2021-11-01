package repatch.github.response

import org.asynchttpclient.Response
import dispatch._
import org.json4s._
import scala.collection.immutable.Map

/**
 * represents pagination.
 */
final case class Paged[A](
    items: Seq[A],
    links: Map[String, String],
    total_count_opt: Option[BigInt],
    incomplete_results_opt: Option[Boolean]
) {
  def next_page: Option[String] = links.get("next")
  def last_page: Option[String] = links.get("last")
  def first_page: Option[String] = links.get("first")
  def prev_page: Option[String] = links.get("prev")
}

object Paged extends Parse {
  implicit def pageToSeq[A](paged: Paged[A]): Seq[A] = paged.items
  val items = 'items.![List[JValue]]
  val total_count_opt = 'total_count.?[BigInt]
  val incomplete_results_opt = 'incomplete_results.?[Boolean]

  def parseArray[A](f: JValue => A): Response => Paged[A] = { (res: Response) =>
    val json = as.json4s.Json(res)
    val links = linkHeader(res)
    Paged(
      json match {
        case JArray(array) => array.map { f(_) }
        case _             => Nil
      },
      links,
      None,
      None
    )
  }

  def parseSearchResult[A](f: JValue => A): Response => Paged[A] = { (res: Response) =>
    val json = as.json4s.Json(res)
    val links = linkHeader(res)
    val xs = items(json) map f
    Paged(xs, links, total_count_opt(json), incomplete_results_opt(json))
  }

  def linkHeader(res: Response): Map[String, String] =
    Map((Option(res.getHeader("Link")) match {
      case Some(s) =>
        s.split(",").toList flatMap { x =>
          x.split(";").toList match {
            case v :: k :: Nil =>
              Some(
                k.trim.replaceAllLiterally("rel=", "").replaceAllLiterally("\"", "") ->
                  v.trim.replaceAllLiterally(">", "").replaceAllLiterally("<", "")
              )
            case _ => None
          }
        }
      case None => Nil
    }): _*)
}
