package repatch.github.request

import dispatch._
import collection.immutable.Map

final case class SearchUsers(q: String, params: Map[String, String] = Map()) extends Method
  with Param[SearchUsers] with SortParam[SearchUsers] with PageParam[SearchUsers] {
  override def complete: Req => Req = _ / "search" / "users" <<? (params + ("q" -> q))
  override def param[A: Show](key: String)(value: A): SearchUsers =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))
}
