package pairiator.repository

import pairiator.Environment
import pairiator.model.Project
import pairiator.model.Commit
import play.api.libs.json.JsPath
import play.api.libs.json.Reads
import org.joda.time.DateTime
import scalaj.http._
import play.api.libs.json._
import play.api.libs.functional.syntax._

class CommitRepository
  extends Environment
  with JodaTimeReader {
  
  implicit val commitReads: Reads[Commit] = (
    (JsPath \ "id").read[String] and
    (JsPath \ "created_at").read[DateTime] and
    (JsPath \ "author_email").read[String]
  )(Commit.apply _)
  
  def listBy(a: Project): List[Commit] = {
    val commits = Http(gitlabUrl + "/projects/" + a.id + "/repository/commits")
      .headers("PRIVATE-TOKEN" -> privateToken)
    
    commits.asString match {
      case HttpResponse(response, 200, _) =>
        Json.parse(response).validate[List[Commit]] match {
          case JsSuccess(allCommits, _) => allCommits
         }
      case _ => List()
    }
  }
}