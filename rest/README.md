Command to run REST: mvn clean install -DskipTests && cd rest && mvn cargo:run \
URL to access  REST: http://localhost:8080/pa165/rest



## Environments
GET http://localhost:8080/pa165/rest/environments \
example: curl -X GET http://localhost:8080/pa165/rest/environments

GET http://localhost:8080/pa165/rest/environments/<id> \
example: curl -X GET http://localhost:8080/pa165/rest/environments/1

DELETE http://localhost:8080/pa165/rest/environments/<id> \
example: curl -X DELETE http://localhost:8080/pa165/rest/environments/1

POST http://localhost:8080/pa165/rest/environments/create (in body: [str] name, [str] description) \
example: curl -X POST http://localhost:8080/pa165/rest/environments/create/ \
            -H 'content-type: application/json' \
            -d '{"name":"dam","description":"100% water"}'

PUT http://localhost:8080/pa165/rest/environments/update/<id> (in body: [str] name, [str] description) \
example: curl -X PUT http://localhost:8080/pa165/rest/environments/update/1 \
             -H 'content-type: application/json' \
             -d '{"name":"dam","description":"100% water"}'

## Animals
GET http://localhost:8080/pa165/rest/animals \
example: curl -X GET http://localhost:8080/pa165/rest/animals

GET http://localhost:8080/pa165/rest/animals/environment/<id> \
example: curl -X GET http://localhost:8080/pa165/rest/animals/environment/1

GET http://localhost:8080/pa165/rest/animals/<id> \
example: curl -X GET http://localhost:8080/pa165/rest/animals/1

DELETE http://localhost:8080/pa165/rest/animals/<id> \
example: curl -X DELETE http://localhost:8080/pa165/rest/animals/1

POST http://localhost:8080/pa165/rest/animals/create (in body: [str] name, [str] species, [long] id) \
example: curl -X POST http://localhost:8080/pa165/rest/animals/create/ \
            -H 'content-type: application/json' \
            -d '{"name":"owl","species":"brown owl","environmentId":2}'

PUT http://localhost:8080/pa165/rest/animals/update/<id> (in body: [str] name, [str] species, [long] id) \
example: curl -X PUT http://localhost:8080/pa165/rest/animals/update/1 \
            -H 'content-type: application/json' \
            -d '{"name":"owl","species":"brown owl","environmentId":2}'

