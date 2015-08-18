package pairiator.service

import javax.inject.Inject
import org.joda.time.LocalDate
import pairiator.model.Commiter

/**
 * @author narcisobenigno
 */
class LatestCommiters @Inject()(latests: LatestPairings) {
  def pairing(since: LocalDate)(implicit info: AuthInfo): Set[Commiter] = {
    latests
      .pairings(since)
      .flatMap{ pairing => List(pairing.pair._1, pairing.pair._2) }
      .toSet
  }
}