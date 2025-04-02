
mvn clean install to make a build
mvn spring-boot:run to start the application
java -jar .\target\Accounts-0.0.1-SNAPSHOT.jar alternative to the above.

## Docker file
1) Add package manager jar or war.
2) create docker file with necessary dependencies
3) run mvn package to create a jar.
4) run docker build . -t aimerick/accounts:s4 to create the docker image

## BuildPacks
1) Add image tag to inside config under maven plugin
2) Run command mvn spring-boot:build-image to create an image

## Google Jib
1) Add dependency
2) mvn compile jib:dockerBuild

## Pushing images to remote repo
docker image push docker.io/aimerick/accounts:s4
docker image push docker.io/aimerick/loans:s4
docker image push docker.io/aimerick/cards:s4

## using docker compose to run all ms
1) add a docker compose file
2) run docker compose up -d ##to start
3) docker compose stop ## to stop running containers inside this network
4) docker compose down ##to stop and remove running containers
5) docker compose start to start existing running containers