package repatch.github.response

import org.json4s._

object TextMatches extends Parse {
  def apply(json: JValue): TextMatches =
    TextMatches(text_matches(json) map TextMatch.apply)

  val text_matches = 'text_matches.![List[JValue]]
}

final case class TextMatches(text_matches: Seq[TextMatch])