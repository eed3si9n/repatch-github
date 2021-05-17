package repatch.github.request

import dispatch._
import scala.collection.immutable.Map

/** AbstractClient is a function to wrap API operations */
abstract class AbstractClient {
  def hostName: String = "api.github.com"
  def host: Req = :/(hostName).secure
  def mimes: Seq[MediaType]
  def httpHeaders(implicit ev: Show[MediaType]): Map[String, String] =
    if (mimes.isEmpty) Map()
    else Map("Accept" -> (mimes map ev.shows).mkString(","))
  def apply(method: Method)(implicit ev: Show[MediaType]): Req = {
    val r = method(host)
    if (httpHeaders.isEmpty) r
    else r <:< httpHeaders
  }
}
