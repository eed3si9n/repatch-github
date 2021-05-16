package repatch.github.request

import java.util.Calendar

trait SortParam[R] { self: Param[R] =>
  val sort      = 'sort.?[String]
  val direction = 'direction.?[String]
  val `type`    = 'type.?[String]
  val since     = 'since.?[Calendar]
  def asc: R    = direction("asc")
  def desc: R   = direction("desc")
}
