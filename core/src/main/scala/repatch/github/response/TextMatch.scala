package repatch.github.response

import org.json4s._

object TextMatch extends Parse {
  def apply(json: JValue): TextMatch =
    TextMatch(
      object_url = object_url(json),
      object_type = object_type(json),
      property = property(json),
      fragment = fragment(json),
      matches = matches(json) map SearchTerm.apply
    )

  val object_url = 'object_url.![String]
  val object_type = 'object_type.![String]
  val property = 'property.![String]
  val fragment = 'fragment.![String]
  val matches = 'matches.![List[JValue]]
}

final case class TextMatch(
  object_url: String,
  object_type: String,
  property: String,
  fragment: String,
  matches: Seq[SearchTerm]
)