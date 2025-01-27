#### MySQL DB related configuration

For the App to connect itself to the MySQL database, we have provided the database credentials as environment variables to the MySQL services's environment attribute so these are hidden from the project source code.

`MYSQL_DATABASE` - The database name
`MYSQL_PASSWORD` - The database's root password, we use the root user account in this example

The environment attributes present in the app service are provided as environment variables to the App container at runtime.

`SPRING_DATASOURCE_URL` - It contains the DB host which is addressable via the mysqldb service name since both services are in the same network. The DB name and port are included in the string too.
`SPRING_DATASOURCE_USERNAME` - The username to access the DB with.
`SPRING_DATASOURCE_PASSWORD` - The password to access the DB with.

Spring Boot automatically assigns these variables to the related application.properties configuration listed below:

```
spring.datasource.url
spring.datasource.username
spring.datasource.password
```

This means we do not have to provide these manually in the application.properties file, so that the App will be connected to the database.

#### Run the application using the Docker Compose command 

Docker Compose will build the Spring Boot and MySQL images, create the containers, create the network, and start them all

`MYSQL_DATABASE=sb_mysql_docker_db MYSQL_PASSWORD=Mysql@2025 docker-compose up`

To stop and remove containers and networks

`docker compose down`

To stop and remove everything created by Docker Compose (Including images and volumes)

`docker compose down --rmi all`

#### Build, Start and Stop the services using shell scripts 

`sh build.sh` To build the project
`sh up.sh` To start the services
`sh down.sh` To stop the services

#### Pushing the image to Docker Hub

###### Step 1: Login to Docker Hub

Use the following command to login and pass your Docker Hub username and password when it prompts

`docker login`

###### Step 2: Tag the Docker Image

Docker images must be tagged with your Docker Hub repository name before pushing. The tag format is:

`<username>/<repository>:<tag>`

Example:

`docker tag <local_image_name> dhandapaniks/<repository_name>:<tag>`
`docker tag dockerize-spring-boot-mysql-app dhandapaniks/dockerize-spring-boot-mysql-app:latest`

###### Step 3: Push the Docker Image

Use the docker push command to upload the image to your Docker Hub repository.

`docker push dhandapaniks/dockerize-spring-boot-mysql-app:latest`

#### References:

https://numericaideas.com/blog/docker-compose-springboot-mysql/

#### Debugging:

https://stackoverflow.com/questions/59838692/mysql-root-password-is-set-but-getting-access-denied-for-user-rootlocalhost
