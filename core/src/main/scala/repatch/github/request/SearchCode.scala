package repatch.github.request

import dispatch._
import collection.immutable.Map

final case class SearchCode(q: String, params: Map[String, String] = Map()) extends Method
  with Param[SearchCode] with SortParam[SearchCode] with PageParam[SearchCode] {
  override def complete: Req => Req = _ / "search" / "code" <<? (params + ("q" -> q))
  override def param[A: Show](key: String)(value: A): SearchCode =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))
}