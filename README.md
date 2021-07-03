# LOCALHOST
>>> sudo nano src/main/resources/application.properties
spring.profiles.active=local
> mvn clean install
> mvn spring-boot:run

# DEVELOPMENT
>>> sudo nano src/main/resources/application.properties
spring.profiles.active=dev
> mvn clean install
>>> (sudo nano /etc/systemd/system/dev-api.service)
> (sudo systemctl daemon-reload) : to make sure systemd get know about new service
> sudo systemctl restart dev-api.service
> sudo systemctl status dev-api.service

# PRODUCTION
>>> sudo nano src/main/resources/application.properties
spring.profiles.active=pro
> mvn clean install
>>> (sudo nano /etc/systemd/system/production-api.service)
> (sudo systemctl daemon-reload) : to make sure systemd get know about new service
> sudo systemctl restart production-api.service
> sudo systemctl status production-api.service

# SYSTEMD:
+ Create
>>> nano /etc/systemd/system/service-name.service
[Unit]
Description=A Spring Boot application, env: DEV, port: 8001!!!
After=syslog.target

[Service]
User=root
ExecStart=/root/DEV/Api/target/api-0.0.1-SNAPSHOT.jar SuccessExitStatus=143

[Install]
WantedBy=multi-user.target

+ Add to system
> sudo systemctl daemon-reload

+ Stop/ Start/ Status
>sudo systemctl stop/start/status myapp.service

