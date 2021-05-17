package repatch.github.request

import dispatch._
import collection.immutable.Map

/**
 * represents git tree request.
 * @see
 *   https://docs.github.com/en/rest/reference/git#trees
 */
final case class GitTrees(repo: Repos, sha: String, params: Map[String, String] = Map())
    extends Method
    with Param[GitTrees] {
  override def complete: Req => Req = repo.complete(_) / "git" / "trees" / sha <<? params
  override def param[A: Show](key: String)(value: A): GitTrees =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))

  val recursive = 'recursive.?[Int]
}
