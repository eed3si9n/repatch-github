package repatch.github.request

import scala.collection.immutable.Map

final case class OAuthClient(token: String, mimes: Seq[MediaType])
    extends AbstractClient
    with Mime[OAuthClient] {
  override def httpHeaders(implicit ev: Show[MediaType]): Map[String, String] =
    super.httpHeaders ++ Map("Authorization" -> "bearer %s".format(token))
  def mime(ms: Seq[MediaType]): OAuthClient = copy(mimes = ms)
}
