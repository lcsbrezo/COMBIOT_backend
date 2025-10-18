# ---- Etapa 1: Build ----
FROM eclipse-temurin:21-jdk AS builder

# Define el directorio de trabajo
WORKDIR /app

# Copiamos los archivos del proyecto
COPY . .

# Construimos el JAR (usa Gradle o Maven según tu proyecto)
# Si usas Gradle:
RUN ./gradlew clean bootJar --no-daemon
# Si usas Maven, reemplaza por:
# RUN ./mvnw clean package -DskipTests

# ---- Etapa 2: Runtime ----
FROM eclipse-temurin:21-jre

# Directorio donde se copiará la app
WORKDIR /app

# Copiamos el jar construido desde la etapa anterior
COPY --from=builder /app/build/libs/*.jar app.jar
# Si usas Maven:
# COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto (cambia si tu app usa otro)
EXPOSE 8080

# Variables de entorno (opcional)
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Comando de ejecución
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
