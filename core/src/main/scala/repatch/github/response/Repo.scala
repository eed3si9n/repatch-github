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
      topics = for {
        xs <- topics_opt(json).toSeq
        JString(x) <- xs
      } yield x,
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
  val topics_opt = 'topics.?[List[JValue]]
  val pushed_at_opt = 'pushed_at.?[Calendar]
}

/**
 * represents repository response.
 * @see
 *   https://docs.github.com/en/rest/reference/repos
 */
final case class Repo(
    id: BigInt,
    // node_id
    url: String,
    owner: User,
    name: String,
    full_name: String,
    description_opt: Option[String],
    `private`: Boolean,
    fork: Boolean,
    html_url: String,
    /*
      "forks_url":"https://api.github.com/repos/dispatch/reboot/forks",
      "keys_url":"https://api.github.com/repos/dispatch/reboot/keys{/key_id}",
      "collaborators_url":"https://api.github.com/repos/dispatch/reboot/collaborators{/collaborator}",
      "teams_url":"https://api.github.com/repos/dispatch/reboot/teams",
      "hooks_url":"https://api.github.com/repos/dispatch/reboot/hooks",
      "issue_events_url":"https://api.github.com/repos/dispatch/reboot/issues/events{/number}",
      "events_url":"https://api.github.com/repos/dispatch/reboot/events",
      "assignees_url":"https://api.github.com/repos/dispatch/reboot/assignees{/user}",
      "branches_url":"https://api.github.com/repos/dispatch/reboot/branches{/branch}",
      "tags_url":"https://api.github.com/repos/dispatch/reboot/tags",
      "blobs_url":"https://api.github.com/repos/dispatch/reboot/git/blobs{/sha}",
      "git_tags_url":"https://api.github.com/repos/dispatch/reboot/git/tags{/sha}",
      "git_refs_url":"https://api.github.com/repos/dispatch/reboot/git/refs{/sha}",
      "trees_url":"https://api.github.com/repos/dispatch/reboot/git/trees{/sha}",
      "statuses_url":"https://api.github.com/repos/dispatch/reboot/statuses/{sha}",
      "languages_url":"https://api.github.com/repos/dispatch/reboot/languages",
      "stargazers_url":"https://api.github.com/repos/dispatch/reboot/stargazers",
      "contributors_url":"https://api.github.com/repos/dispatch/reboot/contributors",
      "subscribers_url":"https://api.github.com/repos/dispatch/reboot/subscribers",
      "subscription_url":"https://api.github.com/repos/dispatch/reboot/subscription",
      "commits_url":"https://api.github.com/repos/dispatch/reboot/commits{/sha}",
      "git_commits_url":"https://api.github.com/repos/dispatch/reboot/git/commits{/sha}",
      "comments_url":"https://api.github.com/repos/dispatch/reboot/comments{/number}",
      "issue_comment_url":"https://api.github.com/repos/dispatch/reboot/issues/comments{/number}",
      "contents_url":"https://api.github.com/repos/dispatch/reboot/contents/{+path}",
      "compare_url":"https://api.github.com/repos/dispatch/reboot/compare/{base}...{head}",
      "merges_url":"https://api.github.com/repos/dispatch/reboot/merges",
      "archive_url":"https://api.github.com/repos/dispatch/reboot/{archive_format}{/ref}",
      "downloads_url":"https://api.github.com/repos/dispatch/reboot/downloads",
      "issues_url":"https://api.github.com/repos/dispatch/reboot/issues{/number}",
      "pulls_url":"https://api.github.com/repos/dispatch/reboot/pulls{/number}",
      "milestones_url":"https://api.github.com/repos/dispatch/reboot/milestones{/number}",
      "notifications_url":"https://api.github.com/repos/dispatch/reboot/notifications{?since,all,participating}",
      "labels_url":"https://api.github.com/repos/dispatch/reboot/labels{/name}",
      "releases_url":"https://api.github.com/repos/dispatch/reboot/releases{/id}",
      "deployments_url":"https://api.github.com/repos/dispatch/reboot/deployments",
     */
    clone_url_opt: Option[String], // does not return during code search
    git_url_opt: Option[String], // does not return during code search
    ssh_url_opt: Option[String], // does not return during code search
    // svn_url":"https://github.com/dispatch/reboot",
    homepage_opt: Option[String],
    language_opt: Option[String],
    forks_count_opt: Option[BigInt],
    // "stargazers_count":433,
    watchers_count_opt: Option[BigInt],
    size_opt: Option[BigInt],
    /*
      "has_issues":true,
      "has_projects":false,
      "has_downloads":true,
      "has_wiki":false,
      "has_pages":true,
      "mirror_url":null,
      "archived":false,
      "disabled":false,
      "license":{
         "key":"lgpl-3.0",
         "name":"GNU Lesser General Public License v3.0",
         "spdx_id":"LGPL-3.0",
         "url":"https://api.github.com/licenses/lgpl-3.0",
         "node_id":"MDc6TGljZW5zZTEy"
      },
      "allow_forking":true,
      "is_template":false,
      "permissions":{
         "admin":false,
         "maintain":false,
         "push":false,
         "triage":false,
         "pull":true
      }
     */
    topics: Seq[String],
    default_branch_opt: Option[String],
    open_issues_count_opt: Option[BigInt],
    pushed_at_opt: Option[java.util.Calendar],
    created_at_opt: Option[java.util.Calendar],
    updated_at_opt: Option[java.util.Calendar]
)

/*
      "topics":[
         "ahc",
         "async",
         "dispatch",
         "http-client",
         "http-library",
         "scala",
         "scala-library"
      ],
   },
]
 */
