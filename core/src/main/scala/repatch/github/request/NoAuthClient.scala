package repatch.github.request

final case class NoAuthClient(mimes: Seq[MediaType])
    extends AbstractClient
    with Mime[NoAuthClient] {
  def mime(ms: Seq[MediaType]): NoAuthClient = copy(mimes = ms)
}
