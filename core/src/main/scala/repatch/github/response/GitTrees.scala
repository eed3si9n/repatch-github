package repatch.github.response

import org.json4s._

object GitTrees extends Parse with CommonField {
  val tree = 'tree.![List[JValue]]

  def apply(json: JValue): GitTrees =
    GitTrees(
      sha = sha(json),
      url = url(json),
      tree = tree(json) map GitTree.apply
    )
}

final case class GitTrees(
  sha: String,
  url: String,
  tree: Seq[GitTree]
)