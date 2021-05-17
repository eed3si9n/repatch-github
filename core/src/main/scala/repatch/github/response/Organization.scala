package repatch.github.response

import java.util.Calendar
import org.json4s._

object Organization extends Parse with CommonField {
  def apply(json: JValue): Organization =
    Organization(
      login = login(json),
      id = id(json),
      repos_url_opt = repos_url_opt(json),
      events_url_opt = events_url_opt(json),
      hooks_url_opt = hooks_url_opt(json),
      issues_url_opt = issues_url_opt(json),
      members_url_opt = members_url_opt(json),
      public_members_url_opt = public_members_url_opt(json),
      avatar_url_opt = avatar_url_opt(json),
      description_opt = description_opt(json),
      url_opt = url_opt(json),
      name_opt = name_opt(json),
      company_opt = company_opt(json),
      blog_opt = blog_opt(json),
      location_opt = location_opt(json),
      email_opt = email_opt(json),
      twitter_username_opt = twitter_username_opt(json),
      is_verified_opt = is_verified_opt(json),
      has_organization_projects_opt = has_organization_projects_opt(json),
      has_repository_projects_opt = has_repository_projects_opt(json),
      public_repos_opt = public_repos_opt(json),
      public_gists_opt = public_gists_opt(json),
      followers_opt = followers_opt(json),
      following_opt = following_opt(json),
      html_url_opt = html_url_opt(json),
      created_at_opt = created_at_opt(json),
      updated_at_opt = updated_at_opt(json),
      type_opt = type_opt(json),
      total_private_repos_opt = total_private_repos_opt(json),
      owned_private_repos_opt = owned_private_repos_opt(json),
      private_gists_opt = private_gists_opt(json),
      disk_usage_opt = disk_usage_opt(json),
      collaborators_opt = collaborators_opt(json),
      billing_email_opt = billing_email_opt(json),
      plan_opt = plan_opt(json) map Plan.apply,
      default_repository_permission_opt = default_repository_permission_opt(json),
      members_can_create_repositories_opt = members_can_create_repositories_opt(json),
      two_factor_requirement_enabled_opt = two_factor_requirement_enabled_opt(json),
      members_allowed_repository_creation_type_opt = members_allowed_repository_creation_type_opt(json),
      members_can_create_public_repositories_opt = members_can_create_public_repositories_opt(json),
      members_can_create_private_repositories_opt = members_can_create_private_repositories_opt(json),
      members_can_create_internal_repositories_opt = members_can_create_internal_repositories_opt(json),
      members_can_create_pages_opt = members_can_create_pages_opt(json),
    )

  // From /organizations
  val hooks_url_opt = 'hooks_url.?[String]
  val issues_url_opt = 'issues_url.?[String]
  val members_url_opt = 'members_url.?[String]
  val public_members_url_opt = 'public_members_url.?[String]

  // Additional items from /orgs/{org}
  val company_opt = 'company.?[String]
  val blog_opt = 'blog.?[String]
  val location_opt = 'location.?[String]
  val twitter_username_opt = 'twitter_username.?[String]
  val is_verified_opt = 'is_verified.?[Boolean]
  val has_organization_projects_opt = 'has_organization_projects.?[Boolean]
  val has_repository_projects_opt = 'has_repository_projects.?[Boolean]
  val public_repos_opt = 'public_repos.?[Int]
  val public_gists_opt = 'public_gists.?[Int]
  val followers_opt = 'followers.?[Int]
  val following_opt = 'following.?[Int]
  val total_private_repos_opt = 'total_private_repos.?[Int]
  val owned_private_repos_opt = 'owned_private_repos.?[Int]
  val private_gists_opt = 'private_gists.?[Int]
  val disk_usage_opt = 'disk_usage.?[BigInt]
  val collaborators_opt = 'collaborators.?[Int]
  val billing_email_opt = 'billing_email.?[String]
  val plan_opt = 'plan.?[JObject]
  val default_repository_permission_opt = 'default_repository_permission.?[String]
  val members_can_create_repositories_opt = 'members_can_create_repositories.?[Boolean]
  val two_factor_requirement_enabled_opt = 'two_factor_requirement_enabled.?[Boolean]
  val members_allowed_repository_creation_type_opt = 'members_allowed_repository_creation_type.?[String]
  val members_can_create_public_repositories_opt = 'members_can_create_public_repositories.?[Boolean]
  val members_can_create_private_repositories_opt = 'members_can_create_private_repositories.?[Boolean]
  val members_can_create_internal_repositories_opt = 'members_can_create_internal_repositories.?[Boolean]
  val members_can_create_pages_opt = 'members_can_create_pages.?[Boolean]
}

final case class Organization(
  login: String,
  id: BigInt,
  //node_id: String,
  repos_url_opt: Option[String],
  events_url_opt: Option[String],
  hooks_url_opt: Option[String],
  issues_url_opt: Option[String],
  members_url_opt: Option[String],
  public_members_url_opt: Option[String],
  avatar_url_opt: Option[String],
  description_opt: Option[String],
  url_opt: Option[String],
  name_opt: Option[String],
  company_opt: Option[String],
  blog_opt: Option[String],
  location_opt: Option[String],
  email_opt: Option[String],
  twitter_username_opt: Option[String],
  is_verified_opt: Option[Boolean],
  has_organization_projects_opt: Option[Boolean],
  has_repository_projects_opt: Option[Boolean],
  public_repos_opt: Option[Int],
  public_gists_opt: Option[Int],
  followers_opt: Option[Int],
  following_opt: Option[Int],
  html_url_opt: Option[String],
  created_at_opt: Option[Calendar],
  updated_at_opt: Option[Calendar],
  type_opt: Option[String],
  total_private_repos_opt: Option[Int],
  owned_private_repos_opt: Option[Int],
  private_gists_opt: Option[Int],
  disk_usage_opt: Option[BigInt],
  collaborators_opt: Option[Int],
  billing_email_opt: Option[String],
  plan_opt: Option[Plan],
  default_repository_permission_opt: Option[String],
  members_can_create_repositories_opt: Option[Boolean],
  two_factor_requirement_enabled_opt: Option[Boolean],
  members_allowed_repository_creation_type_opt: Option[String],
  members_can_create_public_repositories_opt: Option[Boolean],
  members_can_create_private_repositories_opt: Option[Boolean],
  members_can_create_internal_repositories_opt: Option[Boolean],
  members_can_create_pages_opt: Option[Boolean]
)