package repatch.github.response

import org.json4s._

/** provides parsing support for a git blob response. */
object GitBlob extends Parse with CommonField {
  def apply(json: JValue): GitBlob =
    GitBlob(
      sha = sha(json),
      url = url(json),
      encoding = encoding(json),
      content = content(json),
      size = size(json)
    )
}

/** represents git blob response.
 * @see https://docs.github.com/en/rest/reference/git#blobs
 */
final case class GitBlob(
  sha: String,
  url: String,
  encoding: String,
  content: String,
  size: BigInt
) {
  def as_str(charset: String): String =
    encoding match {
      case "base64" => new String(bytes, charset)
      case _ => content
    }

  def as_utf8: String = as_str("UTF-8")

  def bytes: Array[Byte] =
    encoding match {
      case "utf-8"  => content.getBytes
      case "base64" => (new sun.misc.BASE64Decoder()).decodeBuffer(content)
    }
}