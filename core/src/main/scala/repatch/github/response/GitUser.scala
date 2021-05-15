package repatch.github.response

import org.json4s._

object GitUser extends Parse with CommonField {
  def apply(json: JValue): GitUser =
    GitUser(
      name = name(json),
      email = email(json),
      date = date(json)
    )
}

final case class GitUser(
  name: String,
  email: String,
  date: java.util.Calendar
)