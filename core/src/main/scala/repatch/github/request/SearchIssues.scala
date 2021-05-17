package repatch.github.request

import dispatch._
import collection.immutable.Map

final case class SearchIssues(q: String, params: Map[String, String] = Map())
    extends Method
    with Param[SearchIssues]
    with SortParam[SearchIssues]
    with PageParam[SearchIssues] {
  override def complete: Req => Req = _ / "search" / "issues" <<? (params + ("q" -> q))
  override def param[A: Show](key: String)(value: A): SearchIssues =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))
}
