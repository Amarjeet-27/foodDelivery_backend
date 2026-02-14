# Stage 1: build using Maven + Temurin JDK 17
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn -DskipTests -Dmaven.compiler.release=17 clean package -B


# Stage 2: runtime using Temurin JDK 17 (slim Jammy)
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# copy the built jar (use wildcard to avoid hardcoding version)
COPY --from=build /app/target/foodDelivery-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
