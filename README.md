# Would you like to test it? Just run:

Modify the `.env`.

Open the sbt console on project's root dir.
```bash
$ sbt pairiator/run
```

And open [/pairings](http://localhost:8888/pairings) using a header `Token` with
gitlab private token

## How to run with docker

```bash
sbt docker:publishLocal
docker run -p 9990:9990 -p 8888:8888 -e "GITLAB_API=https://your git api goes here/api/v3"  pairiator:0.0.1
```
