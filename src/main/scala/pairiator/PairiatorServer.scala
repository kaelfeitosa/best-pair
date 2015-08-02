package pairiator

import pairiator.controllers.PairingsController
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.HttpServer

class PairiatorServer extends HttpServer {
  override def configureHttp(router: HttpRouter) {
    router.add[PairingsController]
  }  
}