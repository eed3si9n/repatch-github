package repatch.github.response

import org.json4s._

object GitTree extends Parse with CommonField {
  def apply(json: JValue): GitTree =
    GitTree(
      sha = sha(json),
      url = url(json),
      path = path(json),
      mode = mode(json),
      `type` = `type`(json),
      size_opt = size_opt(json)
    )
}

/** represents git tree response
 * @see https://docs.github.com/en/rest/reference/git#trees
 */
final case class GitTree(
  sha: String,
  url: String,
  path: String,
  mode: String,
  `type`: String,
  size_opt: Option[BigInt]
)