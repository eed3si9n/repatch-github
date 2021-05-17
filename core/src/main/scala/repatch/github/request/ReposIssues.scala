package repatch.github.request

import dispatch._
import collection.immutable.Map

final case class ReposIssues(repo: Repos, params: Map[String, String] = Map())
    extends Method
    with Param[ReposIssues]
    with SortParam[ReposIssues]
    with PageParam[ReposIssues] {
  override def complete: Req => Req = repo.complete(_) / "issues" <<? params
  override def param[A: Show](key: String)(value: A): ReposIssues =
    copy(params = params + (key -> implicitly[Show[A]].shows(value)))

  val milestone = 'milestone.?[String]
  val state = 'state.?[IssueState]
  val assignee = 'assignee.?[String]
  val creator = 'creator.?[String]
  val mentioned = 'mentioned.?[String]
  def labels(xs: String*) = param("labels")(xs.toSeq)
}
