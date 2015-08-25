package pairiator

import pairiator.controllers.PairingsController
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.HttpServer
import pairiator.controllers.CommittersController

class PairiatorServer extends HttpServer {
  override def configureHttp(router: HttpRouter) {
    router.add[PairingsController]
    router.add[CommittersController]
  }  
}