package pairiator.model

object Committer {
  def pairBy(a: Commit): (Committer, Committer) = {
    def break(value: String) = value.split("""\+""").map(_.trim)
    
    def takeEmails = {
      val emails = a.authorEmail.split("@")
      
      break(emails.head).map(_ + "@" + emails.last)
    }
    
    def takeNames = break(a.authorName)
    
    (
        Committer(takeEmails.head, takeNames.head),        Committer(takeEmails.last, takeNames.last)
    )
  }
}

case class Committer(name: String, email: String)