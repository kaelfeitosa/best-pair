package pairiator.repository.gitlab

import org.joda.time.DateTime

import pairiator.Environment
import pairiator.model.Project
import play.api.libs.functional.syntax._
import play.api.libs.json._
import scalaj.http._

class ProjectRepository
  extends HttpClient
  with JodaTimeReader {
  
  implicit val projectReads: Reads[Project] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "last_activity_at").read[DateTime]
  )(Project.apply _)
    
  def list(orderBy: String = "last_activity_at", sort: String = "desc"): List[Project] = {
    val projects = request("projects").param("order_by", orderBy).param("sort", sort)
    
    val json = Json.parse(projects.asString.body)
    json.validate[List[Project]] match {
      case JsSuccess(allProjects, _) => allProjects
    } 
  }
}