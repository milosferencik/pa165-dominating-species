Command to run REST: mvn clean install -DskipTests && cd rest && mvn cargo:run
URL to access  REST: http://localhost:8080/pa165/rest

available URLs:

## Environments
GET http://localhost:8080/pa165/rest/environments
GET http://localhost:8080/pa165/rest/environments/<id>
DELETE http://localhost:8080/pa165/rest/environments/<id>
POST http://localhost:8080/pa165/rest/environments/create (in body: [str] name, [str] description)
PUT http://localhost:8080/pa165/rest/environments/update/<id> (in body: [str] name, [str] description)

[DEV purposes]
GET http://localhost:8080/pa165/rest/environments/test

