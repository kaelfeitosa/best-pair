package pairiator.controllers

import org.joda.time.LocalDate
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import pairiator.model.Committer
import pairiator.model.Pairing
import pairiator.repository.gitlab.CommitRepository
import pairiator.repository.gitlab.ProjectRepository
import pairiator.service.LatestPairings
import play.api.libs.functional.syntax._
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json._
import play.api.libs.json.__
import pairiator.service.gitlab.PrivateToken
import javax.inject.Inject

class PairingsController @Inject()(latests: LatestPairings) extends Controller {
  implicit val jodaDateTimeWrites = Writes.jodaDateWrites("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")

  implicit def tuple2Writes = new Writes[Tuple2[Committer, Committer]] {
      def writes(pairs: Tuple2[Committer, Committer]) =
        Json.obj(
            "pilot" -> pairs._1.name,
            "navigator" -> pairs._2.name
        )
    }

  implicit val pairingsWrites: Writes[Pairing] = (
    __.write[(Committer, Committer)] and
    (__ \ "pairings").write[Int]
  )(unlift(Pairing.unapply))

  options("/pairings") { request: Request =>
    response.ok("").contentType("application/hal+json")
      .header("Access-Control-Allow-Origin", "*")
      .header("Access-Control-Allow-Methods", "GET")
      .header("Access-Control-Allow-Headers", "token")
  }

  get("/pairings") { request: Request =>
    implicit val auth = PrivateToken(request.headers().get("Token"))

    val since = request.params.get("since")
      .map(LocalDate.parse(_))
      .getOrElse(LocalDate.now().minusDays(7))

    val json = Json.obj(
        "_embedded" -> Json.obj(
            "pairing" -> Json.toJson(latests.pairings(since))
        )
    )

    response.ok(Json.stringify(json)).contentType("application/hal+json")
      .header("Access-Control-Allow-Origin", "*")
  }
}
