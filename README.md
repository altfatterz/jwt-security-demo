```bash

zoal@zoltans-macbook-pro:~|⇒  echo '{"username":"user", "password":"password"}' | http post :8080/login
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

zoal@zoltans-macbook-pro:~|⇒  http :8080/customers/12451 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTUzMTE2MDI4NH0.sUL4ChYLEExfIKiPOy7XouT4MeWN41m9NWLyz0_0di0'
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
zoal@zoltans-macbook-pro:~|⇒  echo '{"username":"admin", "password":"admin"}' | http post :8080/login
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


```bash
zoal@zoltans-macbook-pro:~|⇒  http :8080/customers/123 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTUzMTE1OTkyNn0.hCdwOQIxd8x-_Yzp8XVfiUKUqtK66wqs06jfi-pNyGw'
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
    "firstName": "Walter",
    "lastName": "White"
}
```