package repatch.github.request

import dispatch._
import repatch.github.{response => res}
import collection.immutable.Map

/** represents a github repository from the request-side
 * @see https://docs.github.com/en/rest/reference/repos
 */
final case class Repos(owner: String, name: String) extends Method {
  def git_refs = GitRefs(this, None)
  def git_commit(ref: res.GitRef): GitCommits = GitCommits(this, ref.git_object.sha)
  def git_commit(sha: String): GitCommits = GitCommits(this, sha)
  def git_trees(commit: res.GitCommit): GitTrees = GitTrees(this, commit.tree.sha)
  def git_trees(sha: String): GitTrees = GitTrees(this, sha)
  def git_blob(sha: String): GitBlobs = GitBlobs(this, sha, MediaType.default)
  def issues: ReposIssues = ReposIssues(this, Map())

  override def complete: Req => Req = _ / "repos" / owner / name
}