package pairiator.repository.gitlab

import pairiator.Environment
import scalaj.http.HttpRequest
import scalaj.http.Http
import pairiator.service.AuthInfo

trait HttpClient extends Environment {
  def request(path: String)(implicit auth: AuthInfo): HttpRequest = {
    auth.apply(Http(gitlabUrl + "/" + path))
  }
}