package repatch.github.response

import org.json4s._
import repatch.github.request.Show

object IssueState {
  def open: IssueState = Open
  def closed: IssueState = Closed
  def all: IssueState = All

  case object Open extends IssueState {
    override def toString: String = "open"
  }
  case object Closed extends IssueState {
    override def toString: String = "closed"
  }
  case object All extends IssueState {
    override def toString: String = "all"
  }

  implicit val stateRead: ReadJs[IssueState] = ReadJs.readJs {
    case JString("open")   => Open
    case JString("closed") => Closed
    case JString("all")    => All
  }
  implicit val stateShow: Show[IssueState] = Show.showA
}

sealed trait IssueState {}
