package repatch.github.response

import java.util.Calendar
import org.json4s._

/**
 * provides parsing support for a github repository response.
 * @see
 *   https://docs.github.com/en/rest/reference/repos
 */
object Repo extends Parse with CommonField {
  def apply(json: JValue): Repo =
    Repo(
      id = id(json),
      url = url(json),
      owner = User(owner(json)),
      name = name(json),
      full_name = full_name(json),
      description_opt = description_opt(json),
      `private` = `private`(json),
      fork = fork(json),
      html_url = html_url(json),
      clone_url_opt = clone_url_opt(json),
      git_url_opt = git_url_opt(json),
      ssh_url_opt = ssh_url_opt(json),
      homepage_opt = homepage_opt(json),
      language_opt = language_opt(json),
      forks_count_opt = forks_count_opt(json),
      watchers_count_opt = watchers_count_opt(json),
      size_opt = size_opt(json),
      default_branch_opt = default_branch_opt(json),
      open_issues_count_opt = open_issues_count_opt(json),
      pushed_at_opt = pushed_at_opt(json),
      created_at_opt = created_at_opt(json),
      updated_at_opt = updated_at_opt(json)
    )

  val full_name = 'full_name.![String]
  val `private` = 'private.![Boolean]
  val fork = 'fork.![Boolean]
  val clone_url_opt = 'clone_url.?[String]
  val git_url_opt = 'git_url.?[String]
  val ssh_url_opt = 'ssh_url.?[String]
  val svn_url_opt = 'svn_url.?[String]
  val mirror_url_opt = 'mirror_url.?[String]
  val homepage_opt = 'homepage.?[String]
  val language_opt = 'language.?[String]
  val forks_count_opt = 'forks_count.?[BigInt]
  val watchers_count_opt = 'watchers_count.?[BigInt]
  val default_branch_opt = 'default_branch.?[String]
  val open_issues_count_opt = 'open_issues_count.?[BigInt]
  val owner = 'owner.![JObject]
  val pushed_at_opt = 'pushed_at.?[Calendar]
}

/**
 * represents repository response.
 * @see
 *   https://docs.github.com/en/rest/reference/repos
 */
final case class Repo(
    id: BigInt,
    url: String,
    owner: User,
    name: String,
    full_name: String,
    description_opt: Option[String],
    `private`: Boolean,
    fork: Boolean,
    html_url: String,
    clone_url_opt: Option[String], // does not return during code search
    git_url_opt: Option[String], // does not return during code search
    ssh_url_opt: Option[String], // does not return during code search
    homepage_opt: Option[String],
    language_opt: Option[String],
    forks_count_opt: Option[BigInt],
    watchers_count_opt: Option[BigInt],
    size_opt: Option[BigInt],
    default_branch_opt: Option[String],
    open_issues_count_opt: Option[BigInt],
    pushed_at_opt: Option[java.util.Calendar],
    created_at_opt: Option[java.util.Calendar],
    updated_at_opt: Option[java.util.Calendar]
)
