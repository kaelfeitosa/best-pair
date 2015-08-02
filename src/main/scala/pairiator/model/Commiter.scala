package pairiator.model

object Commiter {
  def pairBy(a: Commit): (Commiter, Commiter) = {
    val authors = a.authorEmail.split("@").head.split("""\+""")
    
    (Commiter(authors.head), Commiter(authors.last))
  }
}
case class Commiter(name: String)