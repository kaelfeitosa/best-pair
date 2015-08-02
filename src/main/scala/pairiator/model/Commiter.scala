package pairiator.model

object Commiter {
  def pairBy(a: Commit): (Commiter, Commiter) = {
    val emails = a.authorEmail.split("@")
    val authors = emails.head.split("""\+""")
    
    (Commiter(authors.head + "@" + emails.last), Commiter(authors.last + "@" + emails.last))
  }
}
case class Commiter(name: String)