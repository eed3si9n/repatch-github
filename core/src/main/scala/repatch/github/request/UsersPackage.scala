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

  def delete = UsersPackage.UsersPackageDelete(this)
  def deleteVersion(versionId: BigInt) = UsersPackage.UsersPackageVersionDelete(this, versionId)
  def versions = UsersPackage.UsersPackageVersions(this)

}

object UsersPackage {
  final case class UsersPackageDelete(pkg: UsersPackage) extends Method {
    override def complete: Req => Req = { req: Req => pkg.user.complete(req.DELETE) / "packages" / pkg.packageType / pkg.packageName }
  }

  final case class UsersPackageVersionDelete(pkg: UsersPackage, versionId: BigInt) extends Method {
    override def complete: Req => Req = { req: Req => pkg.user.complete(req.DELETE) / "packages" / pkg.packageType / pkg.packageName / "versions" / versionId.toString() }
  }

  final case class UsersPackageVersions(pkg: UsersPackage, params: Map[String, String] = Map()) extends Method with Param[UsersPackageVersions] with PageParam[UsersPackageVersions] {
    override def complete: Req => Req = pkg.user.complete(_) / "packages" / pkg.packageType / pkg.packageName / "versions" <<? params
    override def param[A: Show](key: String)(value: A): UsersPackageVersions =
      copy(params = params + (key -> implicitly[Show[A]].shows(value)))

    def state(value: String) = param("state")(value)
  }
}