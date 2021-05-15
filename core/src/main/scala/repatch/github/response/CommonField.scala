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
  val git_url     = 'git_url.![String]
  val html_url = 'html_url.![String]

  // description can always be null
  val description_opt = 'description.?[String]
}