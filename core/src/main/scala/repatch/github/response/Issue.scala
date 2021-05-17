package repatch.github.response

import java.util.Calendar
import org.json4s._

object Issue extends Parse with CommonField {
  def apply(json: JValue): Issue =
    Issue(
      url = url(json),
      html_url_opt = html_url_opt(json),
      number_opt = number_opt(json),
      state_opt = state_opt(json),
      title_opt = title_opt(json),
      body_opt = body_opt(json),
      user_opt = user_opt(json) map User.apply,
      labels = for {
        xs <- labels_opt(json).toSeq
        x <- xs
      } yield Label(x),
      assignee_opt = assignee_opt(json) map User.apply,
      milestone_opt = milestone_opt(json) map Milestone.apply,
      comments_opt = comments_opt(json),
      pull_request_opt = pull_request_opt(json) flatMap PullRequest.opt,
      closed_at_opt = closed_at_opt(json),
      created_at_opt = created_at_opt(json),
      updated_at_opt = updated_at_opt(json)
    )

  val number_opt = 'number.?[BigInt]
  val state_opt = 'state.?[IssueState]
  val title_opt = 'title.?[String]
  val body_opt = 'body.?[String]
  val user_opt = 'user.?[JObject]
  val labels_opt = 'labels.?[List[JValue]]
  val assignee_opt = 'assignee.?[JObject]
  val milestone_opt = 'milestone.?[JObject]
  val comments_opt = 'comments.?[BigInt]
  val pull_request_opt = 'pull_request.?[JObject]
  val closed_at_opt = 'closed_at.?[Calendar]
}

final case class Issue(
  url: String,
  html_url_opt: Option[String],
  number_opt: Option[BigInt],
  state_opt: Option[IssueState],
  title_opt: Option[String],
  body_opt: Option[String],
  user_opt: Option[User],
  labels: Seq[Label],
  assignee_opt: Option[User],
  milestone_opt: Option[Milestone],
  comments_opt: Option[BigInt],
  pull_request_opt: Option[PullRequest],
  closed_at_opt: Option[Calendar],
  created_at_opt: Option[Calendar],
  updated_at_opt: Option[Calendar]
)
