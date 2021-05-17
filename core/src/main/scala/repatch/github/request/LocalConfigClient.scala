package repatch.github.request

import dispatch._
import scala.util.control.Exception.allCatch

/**
 * OAuthClient using local github config (https://github.com/blog/180-local-github-config).
 */
final case class LocalConfigClient(underlying: OAuthClient)
    extends AbstractClient
    with Mime[LocalConfigClient] {
  override def host: Req = underlying.host
  override def apply(method: Method)(implicit ev: Show[MediaType]): Req = underlying.apply(method)
  override def mimes: Seq[MediaType] = underlying.mimes
  def mime(ms: Seq[MediaType]): LocalConfigClient =
    LocalConfigClient(underlying = OAuthClient(underlying.token, ms))
}

object LocalConfigClient {
  lazy val envToken: Option[String] = sys.env.get("GITHUB_TOKEN")
  lazy val token: Option[String] = gitConfig("github.token")
  def apply(): LocalConfigClient = LocalConfigClient(MediaType.default)
  def apply(mimes: Seq[MediaType]): LocalConfigClient =
    LocalConfigClient(
      OAuthClient(
        envToken orElse token getOrElse sys.error("Token was not found in local config!"),
        mimes
      )
    )

  // https://github.com/defunkt/gist/blob/master/lib/gist.rb#L237
  def gitConfig(key: String): Option[String] =
    allCatch opt {
      Option(System.getenv(key.toUpperCase.replaceAll("""\.""", "_"))) map { Some(_) } getOrElse {
        val p = new java.lang.ProcessBuilder("git", "config", "--global", key).start()
        val reader = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream))
        Option(reader.readLine)
      }
    } getOrElse None
}
