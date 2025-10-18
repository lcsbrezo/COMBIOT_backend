# ---- Etapa 1: Build ----
FROM eclipse-temurin:21-jdk AS builder

# Directorio de trabajo
WORKDIR /app

# Copiar los archivos del proyecto
COPY . .

# Construir el JAR con Maven Wrapper (mvnw)
RUN ./mvnw clean package -DskipTests

# ---- Etapa 2: Runtime ----
FROM eclipse-temurin:21-jre

# Directorio donde correrá la app
WORKDIR /app

# Copiar el JAR generado desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Variables de entorno opcionales
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
