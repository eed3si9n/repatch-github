package repatch.github.response

import org.json4s._

object Organization extends Parse with CommonField {
  def apply(json: JValue): Organization =
    Organization(
      login = login(json),
      id = id(json),
      repos_url_opt = repos_url_opt(json),
      events_url_opt = events_url_opt(json),
      hooks_url_opt = hooks_url_opt(json),
      issues_url_opt = issues_url_opt(json),
      members_url_opt = members_url_opt(json),
      public_members_url_opt = public_members_url_opt(json),
      avatar_url_opt = avatar_url_opt(json),
      description_opt = description_opt(json)
    )

  val login = 'login.![String]
  val repos_url_opt = 'repos_url.?[String]
  val events_url_opt = 'events_url.?[String]
  val hooks_url_opt = 'hooks_url.?[String]
  val issues_url_opt = 'issues_url.?[String]
  val members_url_opt = 'members_url.?[String]
  val public_members_url_opt = 'public_members_url.?[String]
  val avatar_url_opt = 'avatar_url.?[String]
}

final case class Organization(
  login: String,
  id: BigInt,
  //node_id: String,
  repos_url_opt: Option[String],
  events_url_opt: Option[String],
  hooks_url_opt: Option[String],
  issues_url_opt: Option[String],
  members_url_opt: Option[String],
  public_members_url_opt: Option[String],
  avatar_url_opt: Option[String],
  description_opt: Option[String]
)