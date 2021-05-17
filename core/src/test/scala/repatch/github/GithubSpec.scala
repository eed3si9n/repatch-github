package repatch.github

import dispatch.Defaults._
import dispatch._
import org.json4s._
import org.specs2._
import repatch.github.{request => gh}

class GithubSpec extends Specification { def is = args(sequential = true) ^ s2"""
  This is a specification to check the github handler
  
  `gh.repo(:owner, :repo)` should
    return a json object that can be parsed manually                          ${repos1}
    return a json object that can be parsed with extractors                   ${repos2}
    return a json object that can be parsed using `Repo`"                     ${repos3}

  `gh.user.repos` should
    return a json object that can be parsed using `Repos`                     ${repos4}

  `gh.repo(:owner, :repo).git_refs` should
    return a json array that can be parsed using `GitRefs`                    ${references1}

  `gh.repo(:owner, :repo).git_refs.heads(\"main\")` should
    return a json object that can be parsed using `GitRef`                    ${references2}

  `gh.repo(:owner, :repo).git_refs.tags` should
    return a json array that can be parsed using `GitRefs`                    ${reftags1}

  `gh.repo(:owner, :repo).git_commit(:sha)` should
    return a json object that can be parsed using `GitCommit`                 ${commit1}
    return a json object that can be parsed manually                          ${commit2}

  `gh.repo(:owner, :repo).git_commit(git_ref)` should
    return a commit json object for the given `GitRef`                        ${commit3}

  `gh.repo(:owner, :repo).git_trees(:sha)` should
    return a json object that can be parsed using `GitTrees`                  ${trees1}

  `gh.repo(:owner, :repo).git_trees(:sha).recursive(10)` should
    return a json object that contains subdir blobs                           ${recursive1}

  `gh.repo(:owner, :repo).git_trees(commit)` should
    return a tree json object for the given `GitCommit`                       ${trees2}

  `gh.repo(:owner, :repo).git_blob(:sha)` should
    return a json object that can be parsed using `GitBlob`                   ${blob1}

  `gh.repo(:owner, :repo).git_blob(:sha).raw` should
    return raw blob bytes                                                     ${raw1}

  `gh.issues` should
    return a json array that can be parsed using `Issues`                     ${issues1}

  `gh.issues.labels("bug").asc` should
    return a json array that can be parsed using `Issues`                     ${issues2}

  `gh.repo(:owner, :repo).issues` should
    return a json array that can be parsed using `Issues`                     ${issues3}    

  `gh.repo(:owner, :repo).issues.page(1).per_page(1)` should
    return a json array with Link HTTP header for the next page`              ${pagination1}    

  `gh.user` should
    return a json object that can be parsed using `User`                      ${user1}

  `gh.user(:user)` should
    return a json object that can be parsed using `User`                      ${user2}

  `gh.user.orgs` should
    return a json object that can be parsed using `Orgs`                      ${orgs1}

  `gh.user(:user).orgs` should
    return a json object that can be parsed using `Orgs`                      ${orgs2}

  `gh.orgs(:owner)` should
    return a json object that can be parsed using `Organization`              ${orgs3}

  `gh.organizations` should
    return a json object that can be parsed using `Orgs`                      ${organizations1}

  `gh.organizations`.since(:id).per_page(1) should
    return a json object that can be parsed using 'Orgs' with size 1          ${organizations2}

  `gh.orgs(:owner).packages(:packageType, :packageName)` should
    return a json object that can be parsed using `Package`                   ${pkg1}

  `gh.orgs(:owner).packages(:packageType, :packageName).versions` should
    return a json object that can be parsed using `PackageVersions`           ${pkg4}

  `gh.user(:user).packages(:packageType, :packageName)` should
    return a json object that can be parsed using `Package`                   ${pkg2}

  `gh.user(:user).packages(:packageType, :packageName).versions` should
    return a json object that can be parsed using `PackageVersions`           ${pkg3}

  `gh.search.repos("reboot language:scala")` should
    return a json object that can be parsed using `ReposSearch`               ${search1}

  `gh.search.code("case class Req in:file repo:dispatch/reboot")` should
    return a json object that can be parsed using `CodeSearch`                ${search2}
    return a json object that can be parsed using `TextMatches` given
    HTTP header Accept: application/vnd.github.v3.text-match+json             ${search3}

  `gh.search.issues("oauth client access repo:eed3si9n/repatch-github")` should
    return a json object that can be parsed using `IssuesSearch`              ${search4}

  `gh.search.users("eed3si9n")` should
    return a json object that can be parsed using `UsersSearch`               ${search5}
                                                                              """

  lazy val http = Http.default
  lazy val client = gh.LocalConfigClient()
  val user = "dispatch"
  val name = "reboot"
  val tree_sha = "b1193d20d761654b7fc35a48cd64b53aedc7a697"
  val commit_sha = "bcf6d255317088ca1e32c6e6ecd4dce1979ac718"
  val blob_sha = "3baebe52555bc73ad1c9a94261c4552fb8d771cd"

  def repos1 = {
    // `client(repo(user, name))` constructs a request to
    // https://api.github.com/repos/dispatch/reboot
    // Returned json object
    val x = http(client(gh.repo(user, name)) > as.json4s.Json)
    val login = x map { json =>
      for {
        JObject(fs) <- json
        JField("owner", JObject(owner)) <- fs
        JField("login", JString(login)) <- owner
      } yield login
    }
    login().headOption must_== Some("dispatch")
  }

  def repos2 = {
    // Returned json object can also be parsed field-by-field using an extractor
    val x = http(client(gh.repo(user, name)) > as.json4s.Json)
    val o = x map { json =>  
      import repatch.github.response.Repo._
      owner(json)
    }
    {
      import repatch.github.response.User._
      login(o()) must_== "dispatch"
    }
  }

  def repos3 = {
    // Returned json object can then be parsed using `as.repatch.github.response.Repo`,
    // which returns a Repo case class
    val repos = http(client(gh.repo(user, name)) > as.repatch.github.response.Repo)

    repos().full_name must_== "dispatch/reboot"
  }
  
  def repos4 = {
    val repos = http(client(gh.user.repos.asc) > as.repatch.github.response.Repos)
    repos().head.full_name must_!= "foo"
  }

  def references1 = {
    // `client(repos(user, repo).git_refs)` constructs a request to
    // https://api.github.com/repos/dispatch/reboot/git/refs
    // Returned json array can then be parsed using `GitRefs`,  
    // which returns a seqence of GitRef case classes
    val refs = http(client(gh.repo(user, name).git_refs) > as.repatch.github.response.GitRefs)
    val master = (refs() find {_.ref == "refs/heads/main"}).head
    master.git_object.`type` must_== "commit"
  }

  def references2 = {
    val ref = http(client(gh.repo(user, name).git_refs.heads("main")) > as.repatch.github.response.GitRef)
    val master = ref()
    master.ref must_== "refs/heads/main"
  }

  def reftags1 = {
    val refs = http(client(gh.repo(user, name).git_refs.tags) > as.repatch.github.response.GitRefs)
    val zeroEleven = (refs() find {_.ref == "refs/tags/0.11.0"}).head
    zeroEleven.git_object.`type` must_== "commit"
  }
  
  def commit1 = {
    // `client(repos(user, name).git_commit(commit_sha))` constructs a request to
    // https://api.github.com/repos/dispatch/reboot/git/commits/bcf6d255317088ca1e32c6e6ecd4dce1979ac718
    // Returned json object can then be parsed using `GitCommit`,
    // which returns a GitCommit case class
    val commit = http(client(gh.repo(user, name).git_commit(commit_sha)) > as.repatch.github.response.GitCommit)
    commit().committer.name must_== "softprops"
  }
  
  def commit2 = {
    // Returned json object can also be parsed field-by-field using an extractor
    val json = http(client(gh.repo(user, name).git_commit(commit_sha)) > as.json4s.Json)
    val msg = json map { js =>
      import repatch.github.response.GitCommit._
      message(js)
    }
    msg().startsWith("send") must_== true
  }
  
  def commit3 = {
    // this returns a GitRef case class
    val master = http(client(gh.repo(user, name).git_refs.heads("main")) > as.repatch.github.response.GitRef)
    
    // this returns a GitCommit case class
    val commit = http(client(gh.repo(user, name).git_commit(master())) > as.repatch.github.response.GitCommit)
    commit().sha must_== master().git_object.sha
  }
      
  def trees1 = {
    // `client(repos(user, name).git_trees(tree_sha))` constructs a request to
    // https://api.github.com/repos/dispatch/reboot/git/trees/563c7dcea4bbb71e49313e92c01337a0a4b7ce72
    // Returned json object can then be parsed using `GitTrees`,
    // which returns a seqence of GitTree case class
    val trees = http(client(gh.repo(user, name).git_trees(tree_sha)) > as.repatch.github.response.GitTrees)
    import repatch.github.response.GitTree
    trees().tree must contain { tree: GitTree => tree.path must be_==(".gitignore") }
  }

  def recursive1 = {
    // this returns a sequence of GitTree case class
    val trees = http(client(gh.repo(user, name).git_trees(tree_sha).recursive(10)) > as.repatch.github.response.GitTrees)
    import repatch.github.response.GitTree
    trees().tree must contain { tree: GitTree => tree.path must be_==("core/src/main/scala/retry/retries.scala") }
  }
  
  def trees2 = {
    // this returns a GitCommit case class
    val commit = http(client(gh.repo(user, name).git_commit(commit_sha)) > as.repatch.github.response.GitCommit)    
    
    // this returns a seqence of GitTree case class
    val trees = http(client(gh.repo(user, name).git_trees(commit())) > as.repatch.github.response.GitTrees)
    import repatch.github.response.GitTree
    trees().tree must contain { tree: GitTree => tree.path must be_==(".gitignore") }
  }
    
  def blob1 = {
    // `client(repos(user, name).git_blob(blob_sha))` constructs a request to
    // https://api.github.com/repos/dispatch/reboot/git/blobs/3baebe52555bc73ad1c9a94261c4552fb8d771cd
    // Returned json object can then be parsed using `GitBlob`,
    // which returns a GitBlob case class
    val blob = http(client(gh.repo(user, name).git_blob(blob_sha)) > as.repatch.github.response.GitBlob)
    
    // `as_utf8` method makes the assumption that the contained content is encoded in UTF-8.
    (blob().as_utf8 startsWith ".classpath") must_== true
  }
  
  def raw1 = {
    // `client.raw(repo(user, name).git_blob(blob_sha))` constructs a request to
    // https://api.github.com/repos/dispatch/reboot/git/blobs/3baebe52555bc73ad1c9a94261c4552fb8d771cd
    // with "application/vnd.github.VERSION.raw" as http Accept header.
    // This returns raw bytes. You are responsible for figuring out the charset.
    val raw = http(client.raw(gh.repo(user, name).git_blob(blob_sha)) > as.String)

    (raw() startsWith ".classpath") must_== true
  }

  def issues1 = {
    import repatch.github.response.IssueState._
    val iss = http(client(gh.issues) > as.repatch.github.response.Issues)
    iss().head.state_opt must_== Some(open)
  }

  def issues2 = {
    import gh.IssueState._
    val iss = http(client(gh.repo(user, name).issues.state(closed).labels("bug").desc) > as.repatch.github.response.Issues)
    iss().head.state_opt must_== Some(closed)
  }

  def issues3 = {
    import repatch.github.response.IssueState._
    val iss = http(client(gh.repo(user, name).issues) > as.repatch.github.response.Issues)
    iss().head.state_opt must_== Some(open)
  }

  def pagination1 = {
    import repatch.github.response.IssueState._
    val iss = http(client(gh.repo(user, name).issues.page(1).per_page(1)) > as.repatch.github.response.Issues)
    iss().next_page match {
      case Some(next) =>
        val iss2 = http(client(gh.url(next)) > as.repatch.github.response.Issues)
        iss2().head.state_opt must_== Some(open)
      case _ => sys.error("next page was not found")
    }

  }

  def user1 = {
    val usr = http(client(gh.user) > as.repatch.github.response.User)
    usr().login must_!= "foo" 
  }

  def user2 = {
    val usr = http(client(gh.user("eed3si9n")) > as.repatch.github.response.User)
    usr().login must_== "eed3si9n"
  }

  def orgs1 = {
    val orgs = http(client(gh.user.orgs) > as.repatch.github.response.Orgs)
    orgs().head.login must_!= "foo"
  }

  def organizations1 = {
    val orgs = http(client(gh.organizations) > as.repatch.github.response.Orgs)
    val res = orgs()
    (res.size must_== 30) and
      (res.last.id must_== BigInt(3286))
  }

  def organizations2 = {
    val orgs = http(client(gh.organizations.since(BigInt(3286)).per_page(1)) > as.repatch.github.response.Orgs)
    val res = orgs()
    (res.size must_== 1) and
      (res.head.id must_== BigInt(3428))
  }

  def orgs2 = {
    val orgs = http(client(gh.user("eed3si9n").orgs.desc) > as.repatch.github.response.Orgs)
    orgs().head.login must_== "scala"
  }

  def orgs3 = {
    val orgs = http(client(gh.orgs("scala")) > as.repatch.github.response.Organization)
    orgs().login must_== "scala"
  }

  def pkg1 = {
    val pkg = http(client(gh.orgs("sbt").`package`("maven", "org.scala-sbt.io_2.12")) > as.repatch.github.response.Package)
    pkg().name must_== "org.scala-sbt.io_2.12"
  }

  def pkg2 = {
    val pkg = http(client(gh.user("er1c").`package`("maven", "com.example.java-project-example")) > as.repatch.github.response.Package)
    pkg().name must_== "com.example.java-project-example"
  }

  def pkg3 = {
    val pkg = http(client(gh.user("er1c").`package`("maven", "com.example.java-project-example").versions) > as.repatch.github.response.PackageVersions)
    val res = pkg()
    (res.items.size must_== 1) and
      (res.head.name must_== "0.1.0") and
      (res.head.package_type must_== "maven")
  }

  def pkg4 = {
    val pkg = http(client(gh.orgs("sbt").`package`("maven", "org.scala-sbt.io_2.12").versions) > as.repatch.github.response.PackageVersions)
    val res = pkg()
    (res.size must >=(5)) and
      (res.head.package_type must_== "maven")
  }

  def search1 = {
    val repos = http(client(gh.search.repos("reboot language:scala")) > as.repatch.github.response.ReposSearch)
    repos().head.full_name must_== "dispatch/reboot"
  }

  def search2 = {
    val code = http(client(gh.search.code("\"case class Req\" in:file repo:dispatch/reboot")) > 
      as.repatch.github.response.CodeSearch)
    code().head.path must_== "core/src/main/scala/requests.scala"
  }

  def search3 = {
    val code = http(client.text_match(gh.search.code("\"case class Req\" in:file repo:dispatch/reboot")) >
      as.repatch.github.response.TextMatches)
    code().head.text_matches.head.fragment must contain("case class Req") 
  }

  def search4 = {
    val iss = http(client(gh.search.issues("oauth client access repo:eed3si9n/repatch-github")) > as.repatch.github.response.IssuesSearch)
    iss().head.number_opt must_== Some(1)
  }

  def search5 = {
    val users = http(client(gh.search.users("eed3si9n")) > as.repatch.github.response.UsersSearch)
    users().head.login must_== "eed3si9n"
  }
}
