# Contacts Aggregator

This application aggregates contact information from other external APIs.

## Build and Run

### Requirements

To build and run this application you will need:
- Java: you need Java 23. You can use [SDKMAN!](https://sdkman.io/) to manage your Java versions.
- Gradle: you don’t need to install Gradle manually; the ./gradlew (Unix) or gradlew.bat (Windows) 
  will do it for you.

### Tests

To run all the automated tests after a change you can use:
```shell
./gradlew test
```

### Running the Application

First of the application needs a Redis instance to store fallback data. You can use the docker 
compose file to spin-up a local instance:

```shell
docker compose up
```

Then, for development purposes, you can use:
```shell
./gradlew bootRun
```

Or, for a more robust local test, you can use the container:

```shell
./gradlew bootJar
```

```shell
docker image build . -t contacts-aggregator
```

```shell
docker container run 
    --network="host" 
    -e KENECT_LABS_API_BASE_URL=<kenect labs URL>
    -e KENECT_LABS_API_TOKEN=<kenect labs token>
    -p 8080:8080 contacts-aggregator
```

Lastly, on a cloud environment you can use the same container approach, but remove the 
`--network="host"` parameter as its purpose is to connect to the local redis instance.

You can check that the application is properly running by accessing the swagger UI on 
`http://localhost:8080/docs`.

## Technologies and Patterns Used

For technologies I used:
- Java 23
- Gradle
- Spring Boot
- Spring Web
- Resiliency4j
- Redis

And I augmented the clarity of changes with [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/). The types I use are:
- chore: any changes to build or other supporting aspects of the app;
- feat: new features;
- fix: for bug fixes;
- docs: for documentation changes.

# Road Map 

- ~~Integrate with KENECT_LABS contact API.~~
  - Consider doing concurrent requests to gather the pages.
  - Add resiliency strategies.
    - ~~Timeout;~~
    - Retry;
    - Circuit-Break.
- Add fixed token-based authentication to the API.
  - Will this service be used by non-secure clients?
- Add rate-limiting to the API.
- ~~Use Redis to cache the API response.~~
  - ~~Leverage this cache as fallback when the API fails~~.
- ~~Contenarize the application.~~
- Add spring boot actuator to monitor the application.
- Setup a connection-pool the have some warm-up threads.`