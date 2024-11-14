# README 

## Project Dependency


#### To Connect to Database 
- `spring-boot-starter-data-jpa`
- `mysql-connector-j`
#### To Make Validation on Data
- `spring-boot-starter-validation`
#### To Run Project 
- `spring-boot-starter-web`
#### To make boli code
- `lombok`
#### To Convert Data From DTO To Entity 
- `modelmapper`

### To Auth and App Security
- `spring-boot-starter-security`
- `jjwt-impl`
- `jjwt-api`
- `jjwt-jackson`

### To Send Email 
- `spring-boot-starter-mail`
### To Make docs and swagger 
- `springdoc-openapi-starter-webmvc-ui`


---


## App Feature 

1. Make Login with email and encrypted password
2. Make Register
3. Forgot Password 
4. Can Upload Movies Posters
5. Can CRUD on Movies 
6. Data Validation and Error Handling
7. Handle Global and Specify Exception
8. API and Swagger Docs 




## Application.yml File

```yaml
spring:
  application:
    name: MovieAPI
  main:
    banner-mode : off
  logging:
    level:
      root: warn

  datasource:
    url: jdbc:mysql://localhost:3306/movies_2
    username: root
    password: 123456789
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    database-platform: org.hibernate.dialect.MySQLDialect
    show-sql: true
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true

  mail:
    host: smtp.gmail.com
    port: 587
    username: hussein.elbhrawy74@gmail.com
    password: dqwhqjsalxyivoos
    properties:
      mail:
        debug: true
        smtp:
          auth: true
          starttls:
            enable: true

project:
  poster: posters/

base:
  url: "http://localhost:8080"

app:
  jwt-secret: 3a4a037aa3ee8e56470091e5c57201dd8ea08343d0dc91e63302ffb61f0cabee
#  jwt-expiration-milliseconds: 604800000
  jwt-expiration-milliseconds: 900000
  jwt-refresh-expiration-milliseconds: 604800000
```



## Post man Collections

- [Movies API V2](Movies%20API%20V2.postman_collection.json)

