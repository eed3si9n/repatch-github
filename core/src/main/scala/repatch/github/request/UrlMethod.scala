package repatch.github.request

import collection.immutable.Map

/** represents raw URL. used for navigating to the next page in paginated results.
 */
final case class UrlMethod(url: String, params: Map[String, String] = Map()) extends Method
  with Param[UrlMethod] with SortParam[UrlMethod] with PageParam[UrlMethod] {
  def complete = { _ => dispatch.url(url) <<? params }
  def param[A: Show](key: String)(value: A): UrlMethod =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))
}