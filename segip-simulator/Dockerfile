
# ==========================================
# ETAPA 1: COMPILACION CON MAVEN Y JAVA 25
# ==========================================

FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

# Copiar el archivo Maven
COPY pom.xml .

# Descargar dependencias
RUN mvn -B dependency:go-offline

# Copiar codigo fuente
COPY src ./src

# Compilar el proyecto
RUN mvn -B clean package -DskipTests


# ==========================================
# ETAPA 2: EJECUCION CON JAVA 25
# ==========================================

FROM eclipse-temurin:25-jre

WORKDIR /app

# Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Ejecutar como usuario sin privilegios
USER 10001:10001

# Puerto del microservicio
EXPOSE 8081

# Iniciar la aplicacion
ENTRYPOINT ["java", "-jar", "app.jar"]
