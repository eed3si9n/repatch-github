package repatch.github.response

import org.json4s._

trait Parse {
  def parse[A: ReadJs](js: JValue): Option[A] = implicitly[ReadJs[A]].readJs.lift(js)
  def parse_![A: ReadJs](js: JValue): A = parse(js).get
  def parseField[A: ReadJs](key: String)(js: JValue): Option[A] = parse[A](js \ key)
  def parseField_![A: ReadJs](key: String)(js: JValue): A =
    parseField(key)(js) getOrElse sys.error(s"Key $key was not found in ${js.toString}")

  implicit class SymOp(sym: Symbol) {
    def ?[A: ReadJs](js: JValue): Option[A] = parseField[A](sym.name)(js)
    def ?[A: ReadJs]: JValue => Option[A] = ?[A](_)
    def ![A: ReadJs]: JValue => A = parseField_![A](sym.name) _
  }
}
