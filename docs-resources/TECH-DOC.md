# TECHNICAL INFO

## SERVICE MODULE

### Run service app from terminal

To run the spring web service we need to use `bootRun` task from our IDE or from terminal:

```shell
./gradlew service:bootRun
```

## H2

H2 can be used for db in local executions, as a faster/lighter, in memory solution.

## POSTGRES

Postgres can be used for db in containerised or deployed environments.

## DOCKER-COMPOSE

### START APPLICATIONS

```shell
docker-compose up
```

NOTES: In order for postgres/*.sql files to be executed, volumes should be empty. Else we need to
run `docker-compose down --volumes` first
