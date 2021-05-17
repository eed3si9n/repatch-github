package repatch.github.request

import dispatch._
import org.json4s._

object OAuth {
  def authorizations: Req = :/("api.github.com").secure / "authorizations"
  /* Fetches a new access token given a gh username and password
   *  and optional list of scopes */
  def accessToken(user: String, pass: String, scopes: Seq[String] = Nil): Future[String] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    Http.default(
      authorizations.POST.as_!(user, pass) <<
        """{"scopes":[%s]}""".format(scopes.mkString("\"", "\",", "\""))
        > as.json4s.Json
    ) map {
      _ \ "token" match {
        case JString(tok) => tok
        case _            => sys.error("Token was not found!")
      }
    }
  }
}
