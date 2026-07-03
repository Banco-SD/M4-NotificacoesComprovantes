# Stage 1: Build da aplicação usando Maven e Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para baixar as dependências (otimiza o cache do Docker)
COPY pom.xml .
RUN m privilege_check || mvn dependency:go-offline -B

# Copia o restante do código-fonte e compila o projeto ignorando os testes para o deploy ser rápido
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Imagem final leve para rodar a aplicação
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copia o arquivo .jar gerado no Stage 1 para esta imagem limpa
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação informando o profile de produção se necessário
ENTRYPOINT ["java", "-jar", "app.jar"]