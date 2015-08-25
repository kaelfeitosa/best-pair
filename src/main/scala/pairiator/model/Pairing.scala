package pairiator.model

import org.joda.time.LocalDate

object Pairing {
  def by(a: Commit): Pairing = Pairing(Committer.pairBy(a))
}
case class Pairing(pair: (Committer, Committer), pairings: Int = 1)