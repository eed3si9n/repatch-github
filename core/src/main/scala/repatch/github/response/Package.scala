package repatch.github.response

import java.util.Calendar
import org.json4s._

object Package extends Parse with CommonField {
  def apply(json: JValue): Package =
    Package(
      id = id(json),
      name = name(json),
      package_type = package_type(json),
      url = url(json),
      html_url = html_url(json),
      version_count = version_count(json),
      visibility = visibility(json),
      owner_opt = owner_opt(json) map Owner.apply,
      repo_opt = repo_opt(json) map Repo.apply,
      created_at = created_at(json),
      updated_at = updated_at(json)
    )

  val package_type = 'package_type.![String]
  val version_count = 'version_count.![Int]
  val visibility = 'visibility.![String]
}

final case class Package(
  id: BigInt,
  name: String,
  package_type: String,
  url: String,
  html_url: String,
  owner_opt: Option[Owner],
  repo_opt: Option[Repo],
  version_count: Int,
  visibility: String,
  created_at: Calendar,
  updated_at: Calendar
)