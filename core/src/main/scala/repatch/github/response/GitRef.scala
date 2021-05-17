package repatch.github.response

import org.json4s._

/**
 * represents git reference response.
 * @see
 *   https://docs.github.com/en/rest/reference/git#refs
 */
final case class GitRef(
    ref: String,
    url: String,
    git_object: GitObject
)

/** provides parsing support for a git reference response. */
object GitRef extends Parse with CommonField {
  def apply(json: JValue): GitRef =
    GitRef(
      ref = ref(json),
      url = url(json),
      git_object = GitObject(git_object(json))
    )
}
