package repatch.github.request

sealed trait MediaType {}

object MediaType {
  final case class StringMediaType(mediaType: String) extends MediaType {}
  final case class GithubMediaType(
      _version: Option[String],
      _param: Option[String],
      _format: Option[String]
  ) extends MediaType {
    def version(v: String): GithubMediaType = copy(_version = Some(v))
    def param(p: String): GithubMediaType = copy(_param = Some(p))
    def format(f: String): GithubMediaType = copy(_format = Some(f))
    def format_json: GithubMediaType = format("json")
    def no_format: GithubMediaType = copy(_format = None)
    def diff: GithubMediaType = param("diff").no_format
    def patch: GithubMediaType = param("patch").no_format
    def text_match: GithubMediaType = param("text-match").format_json
    def raw_blob: GithubMediaType = param("raw").no_format
    def raw_body: GithubMediaType = param("raw").format_json
    def text_body: GithubMediaType = param("text").format_json
    def html_body: GithubMediaType = param("html").format_json
    def full_body: GithubMediaType = param("full").format_json
  }

  implicit val mediaTypeShow = new Show[MediaType] {
    def shows(a: MediaType): String = a match {
      case StringMediaType(x) => x
      case GithubMediaType(v, p, f) =>
        val version = v map { "." + _ } getOrElse ""
        val param = p map { "." + _ } getOrElse ""
        val fmt = f map { "+" + _ } getOrElse ""
        s"""application/vnd.github$version$param$fmt"""
    }
  }

  /**
   * THe basic json media type and version info: application/json application/vnd.github.v3+json
   */
  val default: Seq[MediaType] = Seq(json, v3.format_json)
  lazy val json: MediaType = StringMediaType("application/json")
  lazy val v3 = githubMediaType.version("v3")
  lazy val githubMediaType = GithubMediaType(None, None, None)
}
