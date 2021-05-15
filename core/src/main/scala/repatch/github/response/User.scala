package repatch.github.response

import org.json4s._

object User extends Parse with CommonField {
  def apply(json: JValue): User =
    User(
      url = url(json),
      login = login(json),
      id = id(json),
      html_url_opt = html_url_opt(json),
      avatar_url_opt = avatar_url_opt(json),
      gravatar_id_opt = gravatar_id_opt(json),
      type_opt = type_opt(json),
      site_admin_opt = site_admin_opt(json),
      name_opt = name_opt(json),
      email_opt = email_opt(json)
    )

  val login = 'login.![String]
  val html_url_opt = 'html_url.?[String]
  val avatar_url_opt = 'avatar_url.?[String]
  val gravatar_id_opt = 'gravatar_id.?[String]
  val site_admin_opt = 'site_admin.?[Boolean]
  val name_opt = 'name.?[String]
  val email_opt = 'email.?[String]
}

final case class User(
  url: String,
  login: String,
  id: BigInt,
  html_url_opt: Option[String],
  avatar_url_opt: Option[String],
  gravatar_id_opt: Option[String],
  type_opt: Option[String],
  site_admin_opt: Option[Boolean],
  name_opt: Option[String],
  email_opt: Option[String]
)