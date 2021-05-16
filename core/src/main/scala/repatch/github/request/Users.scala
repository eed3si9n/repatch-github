package repatch.github.request

import dispatch._

/** represents users request.
 * @see https://docs.github.com/en/rest/reference/users
 */
final case class Users(name: Option[String]) extends Method {
  override def complete: Req => Req = { req: Req =>
    name match {
      case Some(n) => req / "users" / n
      case None    => req / "user"
    }
  }

  def repos: UsersRepos = UsersRepos(this)
  def orgs: UsersOrgs = UsersOrgs(this)
}
