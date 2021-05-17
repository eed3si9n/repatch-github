package repatch.github.response

import java.util.Calendar
import org.json4s._

trait ReadJs[A] {
  import ReadJs.=>?
  val readJs: JValue =>? A
}

object ReadJs {
  type =>?[-A, +B] = PartialFunction[A, B]

  def readJs[A](pf: JValue =>? A): ReadJs[A] = new ReadJs[A] {
    val readJs = pf
  }

  implicit val listRead: ReadJs[List[JValue]] = readJs { case JArray(v) => v }
  implicit val objectRead: ReadJs[JObject] = readJs { case JObject(v) => JObject(v) }
  implicit val bigIntRead: ReadJs[BigInt] = readJs { case JInt(v) => v }
  implicit val intRead: ReadJs[Int] = readJs { case JInt(v) => v.toInt }
  implicit val stringRead: ReadJs[String] = readJs { case JString(v) => v }
  implicit val boolRead: ReadJs[Boolean] = readJs { case JBool(v) => v }
  implicit val calendarRead: ReadJs[Calendar] = readJs { case JString(v) =>
    // iso8601s
    javax.xml.bind.DatatypeConverter.parseDateTime(v)
  }

  implicit def readJsListRead[A: ReadJs]: ReadJs[List[A]] = {
    val f = implicitly[ReadJs[A]].readJs
    readJs {
      case JArray(xs) if xs forall { f.isDefinedAt } =>
        xs map { f.apply }
    }
  }
}
