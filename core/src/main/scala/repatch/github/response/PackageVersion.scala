package repatch.github.response

import java.util.Calendar
import org.json4s._

object PackageVersion extends Parse with CommonField {
  def apply(json: JValue): PackageVersion =
    PackageVersion(
      id = id(json),
      name = name(json),
      url = url(json),
      html_url = html_url(json),
      license_opt = license_opt(json),
      description_opt = description_opt(json),
      created_at = created_at(json),
      updated_at = updated_at(json),
      deleted_at_opt = deleted_at_opt(json),
      package_type = metadata(json) \ "package_type" match { case JString(v) => v }
    )

  val license_opt = 'license.?[String]
  val metadata = 'metadata.![JObject]
  val deleted_at_opt = 'deleted_at.?[Calendar]
}

final case class PackageVersion(
    id: BigInt,
    name: String,
    url: String,
    html_url: String,
    license_opt: Option[String],
    description_opt: Option[String],
    created_at: Calendar,
    updated_at: Calendar,
    deleted_at_opt: Option[Calendar],
    package_type: String,
    //container_tags: Option[Seq[String]],
    //docker_tags: Option[Seq[String]],
)
