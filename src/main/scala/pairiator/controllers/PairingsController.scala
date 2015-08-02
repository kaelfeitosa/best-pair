package pairiator.controllers

import org.joda.time.DateTime
import org.joda.time.LocalDate

import com.twitter.finagle.http._
import com.twitter.finatra.http._

import pairiator.model._
import pairiator.repository._
import pairiator.service.LatestPairings

import play.api.libs.json._
import play.api.libs.functional.syntax._

class PairingsController extends Controller {
  implicit val jodaDateTimeWrites = Writes.jodaDateWrites("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
  
  implicit val commitWrites: Writes[Commiter] =
    (__ \ "author_email").write[String].contramap(_.name)
  
  implicit def tuple2Writes[A, B](implicit a: Writes[A], b: Writes[B]): Writes[Tuple2[A, B]] =
    new Writes[Tuple2[A, B]] {
      def writes(tuple: Tuple2[A, B]) =
        JsArray(Seq(a.writes(tuple._1), b.writes(tuple._2)))
    }
  
  implicit val pairingsWrites: Writes[Pairing] = (
    (__ \ "pair").write[(Commiter, Commiter)] and
    (__ \ "pairings").write[Int]
  )(unlift(Pairing.unapply))

  get("/pairings") { request: Request =>
    val latests = LatestPairings(new ProjectRepository, new CommitRepository)
    
    
    val json = Json.toJson(latests.pairings(LocalDate.now().minusDays(2)))
    
    response.ok(Json.stringify(json)).contentType("application/hal+json")
  }
}