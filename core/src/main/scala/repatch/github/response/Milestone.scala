package repatch.github.response

import org.json4s._

object Milestone extends Parse with CommonField {
  def apply(json: JValue): Milestone =
    Milestone(
      url = url(json),
      number = number(json),
      state = state(json),
      title = title(json),
      description_opt = description_opt(json)
    )

  val number = 'number.![BigInt]
  val state = 'state.![String]
  val title = 'title.![String]
}

final case class Milestone(
  url: String,
  number: BigInt,
  state: String,
  title: String,
  description_opt: Option[String]
)