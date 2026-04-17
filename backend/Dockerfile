# Stage 1: Build Frontend
FROM node:20 AS frontend-build
WORKDIR /frontend
COPY PROJECT_ATS/package*.json ./
RUN npm install
COPY PROJECT_ATS/ ./
RUN npm run build

# Stage 2: Build Backend with Frontend Static Files
FROM maven:3.9.6-eclipse-temurin-17 AS backend-build
WORKDIR /app
COPY backend/pom.xml .
RUN mvn dependency:go-offline

COPY backend/src ./src
# Copy built frontend to spring boot static directory
COPY --from=frontend-build /frontend/build ./src/main/resources/static

RUN mvn clean package -DskipTests

# Stage 3: Run the Application
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=backend-build /app/target/*.jar app.jar

# Create uploads directory
RUN mkdir -p uploads

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]