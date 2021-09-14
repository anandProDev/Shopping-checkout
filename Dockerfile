#
#FROM adoptopenjdk/openjdk11:alpine-slim
#
#COPY . /build/
#WORKDIR /build/
#
#RUN ./gradlew --no-daemon clean build
#
#ENTRYPOINT [ "java","-jar","/app/build/libs/app.jar" ]
#
#
#
#FROM adoptopenjdk/openjdk11:alpine-slim
#RUN apk add gradle
#COPY ./build.gradle.kts ./build.gradle.kts
#RUN gradle --version
#COPY ./ ./
#RUN gradle build
#
#
#FROM adoptopenjdk/openjdk11:alpine-slim
#VOLUME /tmp
#COPY --from=0 "/build/libs/Kotlin-Redis-0.0.1-SNAPSHOT.jar" .
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/shopping-checkout-0.0.1-SNAPSHOT.jar"]


FROM adoptopenjdk/openjdk11:alpine-slim
COPY build/libs/shopping-checkout-0.0.1-SNAPSHOT.jar shopping-checkout-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/shopping-checkout-0.0.1-SNAPSHOT.jar"]
