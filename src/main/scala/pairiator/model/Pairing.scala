package pairiator.model

import org.joda.time.LocalDate

object Pairing {
  def by(a: Commit): Pairing = {
    val commiters = parseCommiters(a.email)
    
    new Pairing(commiters)
  }

  def parseCommiters(email: String): (Commiter, Commiter) = {
    val authors = email.split("@").head.split("""\+""")
    
    (Commiter(authors.head), Commiter(authors.last))
  }
}
case class Pairing(pair: (Commiter, Commiter),parings: Int = 1)