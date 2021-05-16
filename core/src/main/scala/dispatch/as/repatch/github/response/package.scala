package dispatch.as.repatch.github



package object response {
  import org.asynchttpclient.Response
  import dispatch.as.json4s.Json
  import repatch.github.{response => res}

  val Repo:         Response => res.Repo = Json andThen res.Repo.apply
  val Repos:        Response => res.Paged[res.Repo] = res.Paged.parseArray(res.Repo.apply)
  val GitRef:       Response => res.GitRef = Json andThen res.GitRef.apply
  val GitRefs:      Response => res.Paged[res.GitRef] = res.Paged.parseArray(res.GitRef.apply)
  val GitCommit:    Response => res.GitCommit = Json andThen res.GitCommit.apply
  val GitTrees:     Response => res.GitTrees = Json andThen res.GitTrees.apply
  val GitBlob:      Response => res.GitBlob = Json andThen res.GitBlob.apply
  val Issues:       Response => res.Paged[res.Issue] = res.Paged.parseArray(res.Issue.apply)
  val User:         Response => res.User = Json andThen res.User.apply
  val Users:        Response => res.Paged[res.User] = res.Paged.parseArray(res.User.apply)
  val Orgs:         Response => res.Paged[res.Organization] = res.Paged.parseArray(res.Organization.apply)

  val ReposSearch:  Response => res.Paged[res.Repo] = res.Paged.parseSearchResult(res.Repo.apply)
  val CodeSearch:   Response => res.Paged[res.BlobRef] = res.Paged.parseSearchResult(res.BlobRef.apply)
  val IssuesSearch: Response => res.Paged[res.Issue] = res.Paged.parseSearchResult(res.Issue.apply)
  val UsersSearch:  Response => res.Paged[res.User] = res.Paged.parseSearchResult(res.User.apply)
  val TextMatches:  Response => res.Paged[res.TextMatches] = res.Paged.parseSearchResult(res.TextMatches.apply)
}
