package repatch.github.response

import org.json4s._

object GitCommit extends Parse with CommonField {
  def apply(json: JValue): GitCommit =
    GitCommit(
      sha = sha(json),
      url = url(json),
      author = GitUser(author(json)),
      committer = GitUser(committer(json)),
      message = message(json),
      tree = GitShaUrl(tree(json)),
      parents = parents(json) map {GitShaUrl.apply}
    )

  val author = 'author.![JObject]
  val committer = 'committer.![JObject]
  val tree = 'tree.![JObject]
  val parents = 'parents.![List[JValue]]
}

/** represents git commit response.
 * @see https://docs.github.com/en/rest/reference/git#commits
 */
final case class GitCommit(
  sha: String,
  url: String,
  author: GitUser,
  committer: GitUser,
  message: String,
  tree: GitShaUrl,
  parents: Seq[GitShaUrl]
)
