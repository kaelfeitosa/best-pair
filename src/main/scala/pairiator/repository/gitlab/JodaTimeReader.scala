package pairiator.repository.gitlab

import play.api.libs.json.Reads

trait JodaTimeReader {
  implicit val dateTimeReads = Reads.jodaDateReads("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
}