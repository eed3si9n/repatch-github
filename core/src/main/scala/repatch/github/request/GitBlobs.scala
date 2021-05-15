package repatch.github.request

import dispatch._

/** represents git blob request.
 * @see https://docs.github.com/en/rest/reference/git#blobs
 */
final case class GitBlobs(repo: Repos, sha: String, mimes: Seq[MediaType]) extends Method {
  def complete: Req => Req = repo.complete(_) / "git" / "blobs" / sha
}
