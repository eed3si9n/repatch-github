package repatch.github.response

import org.json4s._

object BlobRef extends Parse with CommonField {
  def apply(json: JValue): BlobRef =
    BlobRef(
      sha = sha(json),
      url = url(json),
      name = name(json),
      path = path(json),
      git_url = git_url(json),
      html_url = html_url(json),
      repository = Repo(repository(json))
    )

  val repository = 'repository.![JObject]
}

final case class BlobRef(
  sha: String,
  url: String,
  name: String,
  path: String,
  git_url: String,
  html_url: String,
  repository: Repo
)
