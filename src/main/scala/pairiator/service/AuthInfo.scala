package pairiator.service

import scalaj.http.HttpRequest

/**
 * @author narcisobenigno
 */
trait AuthInfo {
  def apply(request: HttpRequest): HttpRequest 
}