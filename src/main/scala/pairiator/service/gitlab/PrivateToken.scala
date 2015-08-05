package pairiator.service.gitlab

import pairiator.service.AuthInfo
import scalaj.http.HttpRequest

case class PrivateToken(value: String) extends AuthInfo {
  
  override
  def apply(request: HttpRequest): HttpRequest = request.headers("PRIVATE-TOKEN" -> value)
}