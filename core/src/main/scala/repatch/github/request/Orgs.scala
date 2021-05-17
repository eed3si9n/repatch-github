package repatch.github.request

import dispatch._

/** represents a github org from the request-side
 * @see https://docs.github.com/en/rest/reference/orgs
 */
final case class Orgs(owner: String) extends Method {
  def `package`(packageType: String, packageName: String) = OrgsPackage(this, packageType, packageName)

  override def complete: Req => Req = _ / "orgs" / owner
}