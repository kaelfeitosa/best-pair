package pairiator

import pairiator.service._
import pairiator.model._
import pairiator.repository._
import org.joda.time.DateTime

object acceptanceSheet {
  LatestPairings(new ProjectRepository, new CommitRepository)
  	.pairings((new DateTime()).minusDays(7))
}