package repatch.github.response

import org.json4s._

object GitShaUrl extends Parse with CommonField {
  def apply(json: JValue): GitShaUrl = GitShaUrl(sha = sha(json), url = url(json))
}

final case class GitShaUrl(
  sha: String,
  url: String
)