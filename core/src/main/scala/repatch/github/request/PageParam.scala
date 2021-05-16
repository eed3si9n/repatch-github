package repatch.github.request

trait PageParam[R] { self: Param[R] =>
  val page    = 'page.?[Int]
  val per_page = 'per_page.?[Int]
}