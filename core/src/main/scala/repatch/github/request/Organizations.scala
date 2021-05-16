package repatch.github.request

import dispatch._
import collection.immutable.Map

/** represents issues request.
 * @see https://docs.github.com/en/rest/reference/orgs
 */
final case class Organizations(params: Map[String, String] = Map()) extends Method with Param[Organizations] {
  override def complete: Req => Req = _ / "organizations" <<? params
  override def param[A: Show](key: String)(value: A): Organizations =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))

  def since(id: BigInt) = param("since")(id)
  def per_page(count: Int) = param("per_page")(count)
}
