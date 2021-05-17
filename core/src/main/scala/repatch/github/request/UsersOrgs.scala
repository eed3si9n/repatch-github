package repatch.github.request

import collection.immutable.Map

final case class UsersOrgs(user: Users, params: Map[String, String] = Map())
    extends Method
    with Param[UsersOrgs]
    with SortParam[UsersOrgs]
    with PageParam[UsersOrgs] {
  override def complete = user.complete(_) / "orgs" <<? params
  override def param[A: Show](key: String)(value: A): UsersOrgs =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))
}
