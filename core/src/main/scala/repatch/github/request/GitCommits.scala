package repatch.github.request

import dispatch._

/**
 * represents git commit request.
 * @see
 *   https://docs.github.com/en/rest/reference/git#commits
 */
final case class GitCommits(repo: Repos, sha: String) extends Method {
  def complete: Req => Req = repo.complete(_) / "git" / "commits" / sha
}
