package pairiator.model

object Commiter {
  def pairBy(a: Commit): (Commiter, Commiter) = {
    def break(value: String) = value.split("""\+""").map(_.trim)
    
    def takeEmails = {
      val emails = a.authorEmail.split("@")
      
      break(emails.head).map(_ + "@" + emails.last)
    }
    
    def takeNames = break(a.authorName)
    
    (
        Commiter(takeEmails.head, takeNames.head),
        Commiter(takeEmails.last, takeNames.last)
    )
  }
  
  
}
case class Commiter(name: String, email: String)