package repatch.github.request

import dispatch._
import collection.immutable.Map

/**
 * represents git reference request.
 * @see
 *   https://docs.github.com/en/rest/reference/git#refs
 */
final case class GitRefs(repo: Repos, ref: Option[String], params: Map[String, String] = Map())
    extends Method
    with Param[GitRefs] {
  def heads: GitRefs = copy(ref = Some("heads"))
  def heads(branch: String): GitRefs = copy(ref = Some("heads/" + branch))
  def tags: GitRefs = copy(ref = Some("tags"))
  def tags(tag: String): GitRefs = copy(ref = Some("tags/" + tag))

  override val complete: Req => Req = { req: Req =>
    val request = repo.complete(req) / "git" / "refs" <<? params
    ref match {
      case Some(r) => request / r
      case _       => request
    }
  }

  override def param[A: Show](key: String)(value: A): GitRefs =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))
}
