package repatch.github.request

/**
 * @see
 *   https://docs.github.com/en/rest/reference/search
 */
final case class Search() {
  def repos(q: String): SearchRepos = SearchRepos(q)
  def code(q: String): SearchCode = SearchCode(q)
  def issues(q: String): SearchIssues = SearchIssues(q)
  def users(q: String): SearchUsers = SearchUsers(q)
}
