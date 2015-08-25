package pairiator.service

import org.joda.time.LocalDate

import javax.inject.Inject
import pairiator.model.Committer

/**
 * @author narcisobenigno
 */
class LatestCommitters @Inject()(latests: LatestPairings) {
  def pairing(since: LocalDate)(implicit info: AuthInfo): Set[Committer] = {
    latests
      .pairings(since)
      .flatMap{ pairing => List(pairing.pair._1, pairing.pair._2) }
      .toSet
  }
}