# Build stage
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8000
ENV JAVA_TOOL_OPTIONS="-Xmx512m -Xms256m"
ENV SERVER_PORT=8000
CMD ["java", "-jar", "app.jar"]
