package pairiator.service

import scala.math.Ordering
import org.joda.time._
import pairiator.model.Pairing
import pairiator.repository.gitlab._
import javax.inject.Inject

/***
  import pairiator.service._
  import pairiator.model._
  import pairiator.repository._
  import org.joda.time._
  
  val ps = LatestPairings(new ProjectRepository, new CommitRepository)
  ps.pairings(new LocalDate().minusDays(2))
 */

case class LatestPairings @Inject()(projects: ProjectRepository, commits: CommitRepository) {
  implicit val sortByCreatedAt = Ordering.by[DateTime, Long](_.getMillis()).reversed()
  
  def pairings(since: LocalDate)(implicit info: AuthInfo): List[Pairing] = {
    projects.list()
      .takeWhile(_.lastActivityAt.isAfter(since.toDateTimeAtStartOfDay()))
      .flatMap(commits.listBy(_))
      .sortBy(_.createdAt)
      .takeWhile(_.createdAt.isAfter(since.toDateTimeAtStartOfDay()))
      .map { commit => (commit.createdAt.toLocalDate(), Pairing.by(commit)) }
      .groupBy(_._1)
      .map(_._2.toSet.toList).flatten.map(_._2)
      .groupBy(_.pair).values
      .map { pairings => Pairing(pairings.head.pair, pairings.size) }
      .toList
      .sortBy(_.pair._1.name)
  }
}