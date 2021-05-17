package repatch.github.request

import dispatch._
import collection.immutable.Map

/** represents packages request.
 * @see https://docs.github.com/en/rest/reference/packages
 */
final case class OrgsPackage(org: Orgs, packageType: String, packageName: String, params: Map[String, String] = Map()) extends Method with Param[OrgsPackage] {
  override def complete: Req => Req = org.complete(_) / "packages" / packageType / packageName <<? params

  override def param[A: Show](key: String)(value: A): OrgsPackage =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))

  def delete = OrgsPackage.OrgsPackageDelete(this)
  def versions = OrgsPackage.OrgsPackageVersions(this)
}

object OrgsPackage {
  final case class OrgsPackageDelete(pkg: OrgsPackage) extends Method {
    override def complete: Req => Req = { req: Req => pkg.org.complete(req.DELETE) / "packages" / pkg.packageType / pkg.packageName }
  }

  final case class OrgsPackageVersions(pkg: OrgsPackage, params: Map[String, String] = Map()) extends Method with Param[OrgsPackageVersions] with PageParam[OrgsPackageVersions] {
    override def complete: Req => Req = pkg.org.complete(_) / "packages" / pkg.packageType / pkg.packageName / "versions" <<? params
    override def param[A: Show](key: String)(value: A): OrgsPackageVersions =
      copy(params = params + (key -> implicitly[Show[A]].shows(value)))

    def state(value: String) = param("state")(value)
  }
}