FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests clean package spring-boot:repackage
RUN jar tf target/campus-book-exchange-0.0.1-SNAPSHOT.jar | grep -i "Application.class" || true
RUN jar tf target/campus-book-exchange-0.0.1-SNAPSHOT.jar | grep -i "Application.class" || true

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
