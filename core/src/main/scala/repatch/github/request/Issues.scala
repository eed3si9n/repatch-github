package repatch.github.request

import dispatch._
import collection.immutable.Map

/** represents issues request.
 * @see https://docs.github.com/en/rest/reference/issues
 */
final case class Issues(params: Map[String, String] = Map()) extends Method with Param[Issues]
  with SortParam[Issues] with PageParam[Issues] {
  override def complete: Req => Req = _ / "issues" <<? params
  override def param[A: Show](key: String)(value: A): Issues =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))

  val filter  = 'filter.?[String]
  val state   = 'state.?[IssueState]
  def labels(xs: String*) = param("labels")(xs.toSeq)
}
