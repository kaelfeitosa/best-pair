package pairiator.repository

import pairiator.model.Project
import scalaj.http._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import pairiator.Environment

class ProjectRepository extends Environment {
  case class Projects(projects: Seq[Project])
  
  implicit val projectReads: Reads[Project] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "name").read[String]
  )(Project.apply _)
    
  def list(orderBy: String = "last_activity_at", sort: String = "desc"): List[Project] = {
    val projects = Http(gitlabUrl + "/projects")
      .param("order_by", orderBy)
      .param("sort", sort)
      .headers("PRIVATE-TOKEN" -> privateToken)
    
    val json = Json.parse(projects.asString.body)
    json.validate[List[Project]] match {
      case JsSuccess(allProjects, _) => allProjects
    } 
  }
}