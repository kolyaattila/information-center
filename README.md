[![CircleCI](https://circleci.com/gh/kolyaattila/information-center/tree/master.svg)](https://circleci.com/gh/kolyaattila/information-center/tree/master)
[![codecov.io](https://codecov.io/gh/kolyaattila/information-center/branch/master/graphs/badge.svg)](https://codecov.io/gh/kolyaattila/information-center/branch/master)

# Information-center

### Develoment enviroment

1. Create a file `.env` with same enviroment variables from `sample.env` file.
2. Run `mvn clear install` to create the jars.
3. Run docker-compose `docker-compose -f docker-compose.dev.yaml up --build -d`.
4. If you want to run service, before runnig go to `Run/Edit Configurations...` swich on tab `EnvFile` and check `Enable EnvFile` and also add your `.env` file in the table. 

### Prod enviroment

1. Create a file `.env` with same enviroment variables from `sample.env` file.
2. Run `mvn clear install` to create the jars.
3. Run docker-compose `docker-compose -f docker-compose.prod.yaml up --build -d`.


## Security

### Auth service
Authorization responsibilities are completely extracted to separate server, which grants [OAuth2 tokens](https://tools.ietf.org/html/rfc6749) for the backend resource services. Auth Server is used for user authorization as well as for secure machine-to-machine communication inside a perimeter.
We use two types of security

1. [Basic authentication](https://en.wikipedia.org/wiki/Basic_access_authentication) for the clients.
2. [OAuth 2.0 Bearer Token](https://www.oauth.com/oauth2-servers/access-tokens/) for users.


In this project, I use [`Password credentials`](https://tools.ietf.org/html/rfc6749#section-4.3) grant type for users authorization (since it's used only by web project) and [`Client Credentials`](https://tools.ietf.org/html/rfc6749#section-4.4) grant for microservices authorization.

Spring Cloud Security provides convenient annotations and autoconfiguration to make this really easy to implement from both server and client side. For more information consult the [documentation](http://cloud.spring.io/spring-cloud-security/spring-cloud-security.html) and check configuration details in [Auth Server code]().

From the client side, everything works exactly the same as with traditional session-based authorization. You can retrieve `Principal` object from request, check user's roles and other stuff with expression-based access control and `@PreAuthorize` annotation.

Each client in InformationCenter (account-service, course-service, email-service, video-service and browser) has a scope: `server` for backend services, and `ui` - for the browser. So we can also protect controllers from external access, for example:

``` java
@PreAuthorize("#oauth2.hasScope('server')")
```

#### To obtain a authorization token you have to call `/oauth/token` endpoint from Auth-Service

##### Usning Postman
```
POST: http://localhost:8880/auth/oauth/token?grant_type=password&scope=ui&username=user&password=userPassword
Header: Authorization Basic YnJvd3Nlcjo=
```
##### Using CURL
```
curl  --location --request POST \
      --header 'Authorization: Basic YnJvd3Nlcjo=' \
      'http://localhost:8880/auth/oauth/token?grant_type=password&scope=ui&username=user&password=password'
```
##### If you want to obtain an authorization token with scope server you have to change next thing at the request

  1. `grant_type=client_credentials`
  2. `scope=server`
  3. `Authorization: Basic token` (where token is authorization token for that client)

