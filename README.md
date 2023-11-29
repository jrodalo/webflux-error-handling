# Spring WebFlux - Error handling demo

Just a quick demo of one of the many ways to handle errors in a Spring WebFlux application.

## Where do I look?

The error handling bits are in the [`RoutesConfiguration`](https://github.com/jrodalo/webflux-error-handling/blob/main/src/main/java/es/leanmind/errorhandling/RoutesConfiguration.java#L27) and [`ErrorHttpHandler`](https://github.com/jrodalo/webflux-error-handling/blob/main/src/main/java/es/leanmind/errorhandling/errors/ErrorHttpHandler.java#L14-L15) classes. There's also some error mapping in the [`UserHttpHandler`](https://github.com/jrodalo/webflux-error-handling/blob/main/src/main/java/es/leanmind/errorhandling/users/UserHttpHandler.java#L21) class.

## How do I run this?

The project includes a Gradle wrapper. To run the service locally, execute the following command from the root folder:

```shell
./gradlew bootRun
```

Then try some requests to the API:

```http request
GET /api/v1/users/1
```

```http request
GET /api/v1/users/9999
```
