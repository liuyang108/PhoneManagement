# Phone Management Service

This application allows user to manage phones. etc. get all phone numbers or active a phone.

## Description

As backend system. It expose three API(s) as following:

* Get&nbsp;&nbsp;&nbsp;&nbsp; api/v1/phones?page=0&size=10   &nbsp; Find all phones
* Get&nbsp;&nbsp;&nbsp;&nbsp; api/v1/phones/{customerId}  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Find all phones for a customer
* Patch&nbsp; api/v1/phones/{phoneNumber}    &nbsp;&nbsp;&nbsp; Update an existing phone for active/deactive

After application started up, more detail of API documentation can be found ref: http://localhost:8080/swagger-ui/index.html
### Notes: 

Due to time constrains. this is just quick solution.

This service can be further extend to use CQRS/Event souring pattern to build on top of NoSQL database eg. MongoDB to get better performance.

Also assuming security is out of scope for this, those things will be handled by upper level mechanism. eg. API Gateway

## Getting Started

### Dependencies

This has been tested under
*  Ubuntu 21.04
*  OpenJdk 11 
*  Docker 20.10.9
*  Maven 3.6.3

### Frameworks

* H2 as in-memory database
* Spring Boot + JPA + Hateoas + Actuator + OpenApi + Test
* Lombok + Log4j2 + Mockito

### Executing program

Shell script 'runDocker' use to build the app and run on docker
```
./runDocker.sh
```
## Authors

Yang Liu  

## Version History

* 1.0
    * Initial Release

## Acknowledgments

Inspiration, code snippets, etc.
* [Spring HATEOAS](https://github.com/spring-projects/spring-hateoas-examples)
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
