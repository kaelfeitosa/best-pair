package pairiator.controllers

import com.twitter.finatra.http.Controller
import com.twitter.finagle.http.Request
import pairiator.service.gitlab.PrivateToken
import org.joda.time.LocalDate
import play.api.libs.functional.syntax._
import play.api.libs.json._
import javax.inject.Inject
import pairiator.service.LatestCommiters
import play.api.libs.json.Writes
import pairiator.model.Commiter

class CommitersController @Inject()(commiters: LatestCommiters) extends Controller {
  implicit def commiterWrites: Writes[Commiter] = (
      (__ \ "email").write[String] and
      (__ \ "name").write[String]
  )(unlift(Commiter.unapply))
  
  get("/commiters") { request: Request =>
    implicit val auth = PrivateToken(request.headers().get("Token"))
    
    val since = request.params.get("since")
      .map(LocalDate.parse(_))
      .getOrElse(LocalDate.now().minusDays(7))
      
     val json = Json.obj(
        "_embedded" -> Json.obj(
            "commiters" -> Json.toJson(commiters.pairing(since).toList.sortBy(_.name))
        )
    )
    
    response.ok(Json.stringify(json)).contentType("application/hal+json")
  }
}