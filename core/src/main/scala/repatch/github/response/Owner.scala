package repatch.github.response

import org.json4s._

object Owner extends Parse with CommonField {
  def apply(json: JValue): Owner =
    Owner(
      login = login(json),
      id = id(json),
      avatar_url_opt = avatar_url_opt(json),
      gravatar_id_opt = gravatar_id_opt(json),
      url_opt = url_opt(json),
      html_url_opt = html_url_opt(json),
      followers_url_opt = followers_url_opt(json),
      following_url_opt = following_url_opt(json),
      gists_url_opt = gists_url_opt(json),
      starred_url_opt = starred_url_opt(json),
      subscriptions_url_opt = subscriptions_url_opt(json),
      organizations_url_opt = organizations_url_opt(json),
      repos_url_opt = repos_url_opt(json),
      events_url_opt = events_url_opt(json),
      received_events_url_opt = received_events_url_opt(json),
      type_opt = type_opt(json),
      site_admin_opt = site_admin_opt(json)
    )

  // These are API endpoints that vary based upon if a /user or an /org for the owner
}

final case class Owner(
  login: String,
  id: BigInt,
  //node_id: String,
  avatar_url_opt: Option[String],
  gravatar_id_opt: Option[String],
  url_opt: Option[String],
  html_url_opt: Option[String],
  followers_url_opt: Option[String],
  following_url_opt: Option[String],
  gists_url_opt: Option[String],
  starred_url_opt: Option[String],
  subscriptions_url_opt: Option[String],
  organizations_url_opt: Option[String],
  repos_url_opt: Option[String],
  events_url_opt: Option[String],
  received_events_url_opt: Option[String],
  type_opt: Option[String],
  site_admin_opt: Option[Boolean]
)