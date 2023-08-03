# BookLink Back Demo App

This project is an example backend app. It contains best practices that will help you avoid common mistakes and get
developing FAST.

## Prerequisites

- [Java](https://www.oracle.com/java/technologies/downloads/#java17), 17+
- [Gradle](https://gradle.org/) (7.3+)
- [Docker](https://www.docker.com/get-started)
- [Docker compose](https://docs.docker.com/compose/install/)

Docker and Docker compose are non-essential to run the project, but eventually you will have to use them. Better
download them now!

## Getting started

### The basic build

The first thing to do after all was installed is building it.

```gradle clean build```

This line compiles the project, runs the test and builds the project into a ```.jar``` file

This should generate a ```build``` dir, and in ```build/libs``` you should find ```.jar``` files. Those are a built
version of the project that you can run with java.

### Running it

#### Doing it the IntelliJ way

If you open the project with IntelliJ Idea it should automatically detect it's a gradle project and import it. If it
does not right-click the ```gradle.build``` file and import it.

Once the project was imported look for ```src/main/java/com/example/demo/DemoApplication.java``` and click on the green
play.

Voilà, the project is running and in a few seconds all should be ready!

#### Doing it the Docker way

For this step you will obviously need Docker and Docker Compose installed.

Standing on the project root and after having run ```gradle clean build``` spin up the Docker
Compose ```sudo docker-compose up``` or ```docker-compose up```.

This will spin up isolated containers in your machine with everything needed to run the project installed. You can just
start using it.

## Trying it

We will use Swagger to interact with the back-end.

See swagger in localhost:8080/swagger-ui.html.

Play around with it, it's very intuitive!
