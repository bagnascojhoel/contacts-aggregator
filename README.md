# Contacts Aggregator

This application aggregates contact information from other external APIs.

## Build and Run

> TODO

## Technologies and Patterns Used

For technologies I used:
- Java 23
- Gradle
- Spring Boot
- Spring Web

And I augmented the clarity of changes with [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/). The types I use are:
- chore: any changes to build or other supporting aspects of the app;
- feat: new features;
- fix: for bug fixes;
- docs: for documentation changes.

# Road Map 

- Integrate with KENECT_LABS contact API.
  - Consider doing concurrent requests to gather the pages.
  - Add resiliency strategies.
- Add authentication to the API.
  - Will this service be used by non-secure clients?
- Add rate-limiting to the API.
- Use Redis to cache the API response.
  - Leverage this cache to circuit-breaking.
- Contenarize the application.