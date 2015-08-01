package pairiator.service

import scala.math.Ordering
import pairiator.repository.ProjectRepository
import org.joda.time.DateTime
import pairiator.model.Pairing
import pairiator.repository.CommitRepository
import pairiator.model.Commit
import pairiator.model.Commiter
import org.joda.time.ReadableDateTime
import org.joda.time.LocalDate
import org.joda.time.ReadableInstant

/***
  import pairiator.service._
  import pairiator.model._
  import pairiator.repository._
  import org.joda.time.DateTime
  
  val ps = LatestPairings(new ProjectRepository, new CommitRepository)
  ps.pairings((new DateTime()).minusDays(2))
 */

case class LatestPairings(projects: ProjectRepository, commits: CommitRepository) {
  implicit val sortByCreatedAt = Ordering.by[DateTime, Int](_.millisOfDay().get) 
  
  def pairings(since: LocalDate): List[Pairing] = {
    projects.list()
      .takeWhile(_.lastActivityAt.isAfter(since.toDateTimeAtStartOfDay()))
      .flatMap(commits.listBy(_))
      .takeWhile(_.createdAt.isAfter(since.toDateTimeAtStartOfDay()))
      .map { commit => (commit.createdAt.toLocalDate(), Pairing.by(commit)) }
      .groupBy { pairing => pairing._1 }
      .map(_._2.toSet.toList).flatten.map(_._2)
      .groupBy(_.pair).values
      .map { pairings => Pairing(pairings.head.pair, pairings.size) }.toList
  }
}