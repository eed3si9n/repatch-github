package repatch.github.response

import org.json4s._

object PullRequest extends Parse with CommonField {
  def opt(json: JValue): Option[PullRequest] =
    (for {
      JObject(fs) <- json
      JField("url", JString(_)) <- fs.toList
    } yield apply(json)).headOption

  def apply(json: JValue): PullRequest =
    PullRequest(
      url = url(json),
      html_url = html_url(json),
      diff_url = diff_url(json),
      patch_url = patch_url(json)
    )

  val diff_url = 'diff_url.![String]
  val patch_url = 'patch_url.![String]
}

final case class PullRequest(
    url: String,
    html_url: String,
    diff_url: String,
    patch_url: String
)
