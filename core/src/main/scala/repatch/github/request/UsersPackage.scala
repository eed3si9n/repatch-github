package repatch.github.request

import dispatch._
import collection.immutable.Map

/** represents packages request.
 * @see https://docs.github.com/en/rest/reference/packages
 */
final case class UsersPackage(user: Users, packageType: String, packageName: String, params: Map[String, String] = Map()) extends Method with Param[UsersPackage] {
  override def complete: Req => Req = user.complete(_) / "packages" / packageType / packageName <<? params

  override def param[A: Show](key: String)(value: A): UsersPackage =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))
}