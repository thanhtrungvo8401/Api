# LOCALHOST
>>> create src/main/resources/application.properties
spring.profiles.active=local
> mvn clean install
> mvn spring-boot:run

# DEVELOPMENT
>>> create src/main/resources/application.properties
spring.profiles.active=dev
> mvn clean install
> (mvn spring-boot:run)