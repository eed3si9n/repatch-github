package repatch.github.request

import dispatch._

final case class BasicAuthClient(user: String, pass: String, mimes: Seq[MediaType])
    extends AbstractClient
    with Mime[BasicAuthClient] {
  override def host: Req = :/(hostName).secure as_! (user, pass)
  def mime(ms: Seq[MediaType]): BasicAuthClient = copy(mimes = ms)
}
