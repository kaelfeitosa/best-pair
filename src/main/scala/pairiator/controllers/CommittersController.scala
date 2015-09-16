package pairiator.controllers

import com.twitter.finatra.http.Controller
import com.twitter.finagle.http.Request
import pairiator.service.gitlab.PrivateToken
import org.joda.time.LocalDate
import play.api.libs.functional.syntax._
import play.api.libs.json._
import javax.inject.Inject
import pairiator.service.LatestCommitters
import play.api.libs.json.Writes
import pairiator.model.Committer

class CommittersController @Inject()(committers: LatestCommitters) extends Controller {
  implicit def committerWrites: Writes[Committer] = (
      (__ \ "email").write[String] and
      (__ \ "name").write[String]
  )(unlift(Committer.unapply))

  options("/committers") { request: Request =>
    response.ok("").contentType("application/hal+json")
      .header("Access-Control-Allow-Origin", "*")
      .header("Access-Control-Allow-Methods", "GET")
      .header("Access-Control-Allow-Headers", "token")
  }

  get("/committers") { request: Request =>
    implicit val auth = PrivateToken(request.headers().get("Token"))

    val since = request.params.get("since")
      .map(LocalDate.parse(_))
      .getOrElse(LocalDate.now().minusDays(7))

     val json = Json.obj(
        "_embedded" -> Json.obj(
            "committers" -> Json.toJson(committers.pairing(since).toList.sortBy(_.name))
        )
    )

    response.ok(Json.stringify(json)).contentType("application/hal+json")
      .header("Access-Control-Allow-Origin", "*")
  }
}
