package repatch.github.request

import collection.immutable.Map

final case class UsersRepos(user: Users, params: Map[String, String] = Map()) extends Method
  with Param[UsersRepos] with SortParam[UsersRepos] with PageParam[UsersRepos] {
  override def complete = user.complete(_) / "repos" <<? params
  override def param[A: Show](key: String)(value: A): UsersRepos =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))
}