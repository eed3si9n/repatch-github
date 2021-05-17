package repatch.github.response

import org.json4s._

object Plan extends Parse with CommonField {
  def apply(json: JValue): Plan =
    Plan(
      name = name(json),
      space = space(json),
      private_repos = private_repos(json),
      filled_seats = filled_seats(json),
      seats = seats(json),
    )

  val space = 'space.![Int]
  val private_repos = 'private_repos.![Int]
  val filled_seats = 'filled_seats.![Int]
  val seats = 'seats.![Int]
}

final case class Plan(
    name: String,
    space: Int,
    private_repos: Int,
    filled_seats: Int,
    seats: Int
)
