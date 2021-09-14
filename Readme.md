# Overview


The following is an MVP for a supermarket checkout :

The application contains 2 controllers, one for adding items to the shopping cart and updating/removing them
The second controller to do calculate the amount to be paid at checkout

# Getting Started

Run the following commands to start the application
```
./gradlew clean build
./gradlew bootRun
```


If you prefer using docker, then run the following command
```
./gradlew clean build
docker build --tag=shopping:latest .
docker run -p8080:8080 shopping:latest
```

### Reference Documentation

Swagger:
http://localhost:8080/swagger-ui.html#/
