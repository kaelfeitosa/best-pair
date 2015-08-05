package pairiator.repository.gitlab

import org.joda.time.DateTime

import pairiator.Environment
import pairiator.model.Commit
import pairiator.model.Project
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scalaj.http._

class CommitRepository
  extends HttpClient
  with JodaTimeReader {
  
  implicit val commitReads: Reads[Commit] = (
    (JsPath \ "id").read[String] and
    (JsPath \ "created_at").read[DateTime] and
    (JsPath \ "author_email").read[String]
  )(Commit.apply _)
  
  def listBy(a: Project): List[Commit] = {
    val commits = request("projects/" + a.id + "/repository/commits")
    
    commits.asString match {
      case HttpResponse(response, 200, _) =>
        Json.parse(response).validate[List[Commit]] match {
          case JsSuccess(allCommits, _) => allCommits
         }
      case _ => List()
    }
  }
}