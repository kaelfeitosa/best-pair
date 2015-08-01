package pairiator.model

import org.joda.time.DateTime

case class Commit(id: String, createdAt: DateTime, email: String)