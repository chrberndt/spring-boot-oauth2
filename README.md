# spring-boot-oauth2

Explore and demonstrate how to protect REST endpoints with OAuth2.

# System Requirements

JDK 17
Docker & docker-compose


# How to Run

## Startup Spring Boot backend

```shell
# Start the Spring Boot backend

./gradlew bootRun
```

This will automatically launch a Keycloak server (as configured in `compose.yml`)

Keycloak server is then accessible at http://localhost:8180 with the following credentials:

```bash
# user: temp-admin
# password: admin
```

## Example realm setup

The example realm `spring-boot` is created automatically during startup. The configuration for the `spring-boot` realm is located at 

```tree
docker/
└── keycloak
    └── realm-import.json
```

## Convince yourself that the endpoint `http://localhost:8080/api/test` is protected

```bash
curl -I http://localhost:8080/api/test
```
which will return a 401 Unauthorized response as follows:

```bash
HTTP/1.1 401
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
WWW-Authenticate: Bearer
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 0
Date: Sat, 28 Dec 2024 20:41:48 GMT
```


## Obtain a Valid Access Token from Keykloak

and store it in a shell variable:

```shell
export access_token=$(\
curl -X POST http://localhost:8180/realms/spring-boot/protocol/openid-connect/token \
-H 'content-type: application/x-www-form-urlencoded' \
-d 'client_id=spring-boot-api&client_secret=secret' \
-d 'username=alice&password=secret&grant_type=password' | jq --raw-output '.access_token' \
)
```
The `token_endpoint` property can be obtained from the realm's well-known/openid-configuration e.g.
http://localhost:8180/realms/spring-boot/.well-known/openid-configuration

## Use This Token to Query a Protected Endpoint

```bash
curl http://localhost:8080/api/test \
  -H "Authorization: Bearer "$access_token | jq
```

The response will return an object with claims included in the token which looks as follows:

```json
{
  "sub": "241e0a58-1a5e-40a1-ab9d-39feec7c543c",
  "email_verified": false,
  "roles": [
    "admin",
    "user"
  ],
  "iss": "http://localhost:8180/realms/spring-boot",
  "typ": "Bearer",
  "preferred_username": "alice",
  "given_name": "Alice",
  "sid": "a308d2c0-2b2b-4368-bafa-1259f7d07d97",
  "acr": "1",
  "realm_access": {
    "roles": [
      "admin",
      "user"
    ]
  },
  "azp": "spring-boot-api",
  "scope": "profile email",
  "name": "Alice Liddel",
  "exp": "2024-12-28T20:51:40Z",
  "session_state": "a308d2c0-2b2b-4368-bafa-1259f7d07d97",
  "iat": "2024-12-28T20:46:40Z",
  "family_name": "Liddel",
  "jti": "a8f5b7c5-6d03-4920-927f-1989d3552f2b",
  "email": "alice@example.com"
}
```

# Required Spring Boot Dependencies

```groovy
'org.springframework.boot:spring-boot-starter-oauth2-client'
'org.springframework.boot:spring-boot-starter-security'
'org.springframework.boot:spring-boot-starter-web'
'org.springframework.security:spring-security-oauth2-resource-server'
```

`spring-security-oauth2-resource-server` required to provide BearerTokenResolver

# Implementing the controller

```groovy
/api/test
```

returns an array of type value pairs based on the JWT's claims.

Similar to the `/api/test` endpoint used by
https://github.com/jeroenheijmans/sample-angular-oauth2-oidc-with-auth-guards
and meant to serve as a local alternative to the endpoint hosted at
https://demo.duendesoftware.com/api/test
