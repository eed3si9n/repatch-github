package repatch.github.response

import org.json4s._

object SearchTerm extends Parse {
  def apply(json: JValue): SearchTerm =
    SearchTerm(
      text = text(json),
      indices = indices(json)
    )

  val text = 'text.![String]
  val indices = 'indices.![List[BigInt]]
}

final case class SearchTerm(
    text: String,
    indices: Seq[BigInt]
)
