# ---- Etapa 1: Build ----
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY . .

# ✅ Dar permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Construir el JAR con Maven Wrapper
RUN ./mvnw clean package -DskipTests

# ---- Etapa 2: Runtime ----
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms256m -Xmx512m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]