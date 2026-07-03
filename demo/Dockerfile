# Stage 1: Build da aplicação usando Maven e Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para baixar as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código-fonte e compila FORÇANDO o modo de produção do Vaadin
COPY src ./src
RUN mvn clean package -Pproduction -DskipTests

# Stage 2: Imagem final leve para rodar a aplicação
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copia o arquivo .jar gerado no Stage 1
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
