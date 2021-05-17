package repatch.github.response

import java.util.Calendar
import org.json4s._

trait CommonField { self: Parse =>
  val id = 'id.![BigInt]
  val sha = 'sha.![String]
  val url = 'url.![String]
  val ref = 'ref.![String]

  val path = 'path.![String]
  val mode = 'mode.![String]
  val `type` = 'type.![String]
  val type_opt = 'type.?[String]
  val size = 'size.![BigInt]
  val size_opt = 'size.?[BigInt]
  val message = 'message.![String]
  val name = 'name.![String]
  val email = 'email.![String]
  val date = 'date.![Calendar]
  val created_at = 'created_at.![Calendar]
  val created_at_opt = 'created_at.?[Calendar]
  val updated_at = 'updated_at.![Calendar]
  val updated_at_opt = 'updated_at.?[Calendar]
  val encoding = 'encoding.![String]
  val content = 'content.![String]
  val git_object = 'object.![JObject]
  val git_url = 'git_url.![String]
  val url_opt = 'url.?[String]
  val html_url = 'html_url.![String]

  val login = 'login.![String]
  val avatar_url_opt = 'avatar_url.?[String]
  val gravatar_id_opt = 'gravatar_id.?[String]
  val site_admin_opt = 'site_admin.?[Boolean]
  val html_url_opt = 'html_url.?[String]
  val name_opt = 'name.?[String]
  val email_opt = 'email.?[String]
  val followers_url_opt = 'followers_url.?[String]
  val following_url_opt = 'following_url.?[String]
  val gists_url_opt = 'gists_url.?[String]
  val starred_url_opt = 'starred_url.?[String]
  val subscriptions_url_opt = 'starred_url.?[String]
  val organizations_url_opt = 'organizations_url.?[String]
  val repos_url_opt = 'repos_url.?[String]
  val events_url_opt = 'events_url.?[String]
  val received_events_url_opt = 'events_url.?[String]

  // description can always be null
  val description_opt = 'description.?[String]

  val owner_opt = 'owner.?[JObject]
  val repo_opt = 'repository.?[JObject]
}
