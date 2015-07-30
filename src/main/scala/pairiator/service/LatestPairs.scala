package pairiator.service

import pairiator.repository.ProjectRepository
import org.joda.time.DateTime
import pairiator.model.Pairing

case class LatestCommits(projects: ProjectRepository, commits: CommitRepository) {
  def pairings(since: DateTime): List[Pairing] = {
    val recentlyProjects = projects.list()
    recentlyProjects
      .filter(_.lastActivityAt.isAfter(since))
      .flatMap(_)
  }
}