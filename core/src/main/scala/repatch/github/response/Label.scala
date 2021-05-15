package repatch.github.response

import org.json4s._

object Label extends Parse with CommonField {
  def apply(json: JValue): Label =
    Label(
      url = url(json),
      name = name(json),
      color = color(json)
    )

  val color = 'color.![String]
}

final case class Label(
  url: String,
  name: String,
  color: String
)
