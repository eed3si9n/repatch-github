package repatch.github.request

import dispatch._
import collection.immutable.Map

final case class SearchRepos(q: String, params: Map[String, String] = Map()) extends Method
  with Param[SearchRepos] with SortParam[SearchRepos] with PageParam[SearchRepos] {
  override def complete: Req => Req = _ / "search" / "repositories" <<? (params + ("q" -> q))
  override def param[A: Show](key: String)(value: A): SearchRepos =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))
}