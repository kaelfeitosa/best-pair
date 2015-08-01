# Would you like to test it? Just run:

Modify the `.env`.

Open the sbt console on project's root dir.
```bash
$ sbt console
```

And type the scala code below:
```scala
import pairiator.service._
import pairiator.model._
import pairiator.repository._
import org.joda.time._

val ps = LatestPairings(new ProjectRepository, new CommitRepository)
ps.pairings(new LocalDate().minusDays(2))
```

