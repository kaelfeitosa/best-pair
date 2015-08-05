package pairiator.controllers

import org.joda.time.LocalDate

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import pairiator.model.Commiter
import pairiator.model.Pairing
import pairiator.repository.gitlab.CommitRepository
import pairiator.repository.gitlab.ProjectRepository
import pairiator.service.LatestPairings
import play.api.libs.functional.syntax._
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.Writes
import play.api.libs.json.__

class PairingsController extends Controller {
  implicit val jodaDateTimeWrites = Writes.jodaDateWrites("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
  
  implicit def tuple2Writes = new Writes[Tuple2[Commiter, Commiter]] {
      def writes(pairs: Tuple2[Commiter, Commiter]) =
        Json.obj(
            "pilot" -> pairs._1.name,
            "navigaror" -> pairs._2.name
        )
    }
  
  implicit val pairingsWrites: Writes[Pairing] = (
    __.write[(Commiter, Commiter)] and
    (__ \ "pairings").write[Int]
  )(unlift(Pairing.unapply))

  get("/pairings") { request: Request =>
    val latests = LatestPairings(new ProjectRepository, new CommitRepository)
    
    val since = request.params.get("since")
      .map(LocalDate.parse(_))
      .getOrElse(LocalDate.now().minusDays(7))
    
    val json = Json.obj(
        "_embedded" -> Json.obj(
            "pairing" -> Json.toJson(latests.pairings(since))
        )
    )
    
    response.ok(Json.stringify(json)).contentType("application/hal+json")
  }
}