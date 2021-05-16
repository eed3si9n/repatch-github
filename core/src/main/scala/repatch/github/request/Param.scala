package repatch.github.request

import collection.immutable.Map

trait Param[R] {
  val params: Map[String, String]
  def param[A: Show](key: String)(value: A): R
  implicit class SymOp(sym: Symbol) {
    def ?[A: Show]: A => R = param(sym.name)(_)
  }
}