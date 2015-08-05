package pairiator.repository.gitlab

import pairiator.Environment
import scalaj.http.HttpRequest
import scalaj.http.Http

trait HttpClient extends Environment {
  def request(path: String): HttpRequest = {
    Http(gitlabUrl + "/" + path)
      .headers("PRIVATE-TOKEN" -> privateToken)
  }
}