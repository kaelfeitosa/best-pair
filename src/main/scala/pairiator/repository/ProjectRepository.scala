package pairiator.repository

import pairiator.model.Project
import scalaj.http._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import pairiator.Environment
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

class ProjectRepository extends Environment {
  case class Projects(projects: Seq[Project])

  implicit val dateTimeReads = Reads.jodaDateReads("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
  
  implicit val projectReads: Reads[Project] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "last_activity_at").read[DateTime]
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