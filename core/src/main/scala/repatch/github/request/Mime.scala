package repatch.github.request

trait Mime[R] {
  import MediaType._
  def mime(ms: Seq[MediaType]): R
  def raw = mime(Seq(current_version.raw_blob))
  def diff = mime(Seq(current_version.diff))
  def patch = mime(Seq(current_version.patch))
  def raw_body = mime(Seq(json, current_version.raw_body))
  def text_body = mime(Seq(json, current_version.text_body))
  def html_body = mime(Seq(json, current_version.html_body))
  def full_body = mime(Seq(json, current_version.full_body))
  def text_match = mime(Seq(json, current_version.text_match))
  def current_version = v3
}
