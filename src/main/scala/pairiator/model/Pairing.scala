package pairiator.model

import org.joda.time.LocalDate

object Pairing {
  def by(a: Commit): Pairing = Pairing(Commiter.pairBy(a))
}
case class Pairing(pair: (Commiter, Commiter), pairings: Int = 1)