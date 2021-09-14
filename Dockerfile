
FROM adoptopenjdk/openjdk11:alpine-slim
COPY build/libs/shopping-checkout-0.0.1-SNAPSHOT.jar shopping-checkout-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/shopping-checkout-0.0.1-SNAPSHOT.jar"]
