package repatch.github.response

import org.json4s._

object GitObject extends Parse with CommonField {
  def apply(json: JValue): GitObject =
    GitObject(
      sha = sha(json),
      url = url(json),
      `type` = `type`(json)
    )
}

final case class GitObject(
    sha: String,
    url: String,
    `type`: String
)
