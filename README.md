```bash

echo '{"username":"user", "password":"password"}' | http post :8080/login

HTTP/1.1 200
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTUzMTE2MDI4NH0.sUL4ChYLEExfIKiPOy7XouT4MeWN41m9NWLyz0_0di0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Date: Mon, 09 Jul 2018 17:18:04 GMT
Expires: 0
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block

```

```bash

http :8080/customers/12451 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTUzMTE2MDI4NH0.sUL4ChYLEExfIKiPOy7XouT4MeWN41m9NWLyz0_0di0'

HTTP/1.1 403
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Type: application/json;charset=UTF-8
Date: Mon, 09 Jul 2018 17:20:43 GMT
Expires: 0
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block

{
    "error": "Forbidden",
    "message": "Forbidden",
    "path": "/customers/12451",
    "status": 403,
    "timestamp": "2018-07-09T17:20:43.271+0000"
}
```


```bash
echo '{"username":"admin", "password":"admin"}' | http post :8080/login

HTTP/1.1 200
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiZXhwIjoxNTMxMTU5OTM3fQ.LZ548G0-bOQMetndgrtNludEcEQtXq68WPHrjDkzBwI
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Date: Mon, 09 Jul 2018 17:12:17 GMT
Expires: 0
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block

```

`/customers/{customerId}` calls `contract-service` propagating the JWT token

```bash
http :8080/customers/123 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTUzMTE1OTkyNn0.hCdwOQIxd8x-_Yzp8XVfiUKUqtK66wqs06jfi-pNyGw'

HTTP/1.1 200
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Type: application/json;charset=UTF-8
Date: Mon, 09 Jul 2018 17:12:38 GMT
Expires: 0
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block

{
    "contract": "best contract",
    "firstName": "Walter",
    "lastName": "White"
}
```

-----------------------------------------------------------------

access the customer without the `auth-service` since the `edge-service` can create the JWT token

```
http :8080/customers

HTTP/1.1 200
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Type: application/json;charset=UTF-8
Date: Thu, 06 Sep 2018 08:27:40 GMT
Expires: 0
Pragma: no-cache
Set-Cookie: JSESSIONID=572A2998B49018157A29D5C816019E30; Path=/; HttpOnly
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block

[
    {
        "contract": "hello",
        "firstName": "John",
        "lastName": "Doe"
    }
]
```
















```
breaking point here: AbstractConfiguredSecurityBuilder.init()

SpringBootWebSecurityConfiguration
```

```
WebSecurityConfigurerAdapter
```


```error received:
Caused by: java.lang.NoSuchMethodException: com.example.jwt.filter.JwtHttpSecurityConfigurer.<init>()


```

JWT vs OAuth

- JWT is a type of token (is a token format for carrying claims) and OAuth is framework that describes how to distribute tokens
- SAML assertions are similar to JWT claims
- SON Web Tokens are an open, industry standard (https://tools.ietf.org/html/rfc7519) method for representing claims securely between two parties.
- JWT can be used as an OAuth Bearer token
- It is not possible to revoke JWT. They expire when they are marked to expire at creation time.
- If you have to go to an external server to authenticate each API request and that call can be expensive, JWT can get you a better performance.
- The flip side to having self validating tokens is any invalidation of these tokens will not have immediate effect. As a side effect, you are forced to have lower expiry times to these tokens ( may not be ideal in some cases)
- very good explanation: https://community.apigee.com/questions/21139/jwt-vs-oauth.html



