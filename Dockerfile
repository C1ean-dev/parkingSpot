# Etapa 1: Construir o JAR com Maven
FROM eclipse-temurin:17-alpine as build

# Diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências do Maven
COPY pom.xml . 
RUN apk add --no-cache maven  # Instalar Maven na imagem Alpine
RUN mvn dependency:go-offline  # Baixar dependências

# Copia o código-fonte para dentro do container
COPY src /app/src

# Executa o build do Maven para gerar o JAR
RUN mvn clean package -DskipTests

# Etapa 2: Executar a aplicação com o JAR gerado
FROM eclipse-temurin:17-alpine

# Diretório de trabalho
WORKDIR /app

# Copia o JAR gerado na etapa anterior para o container
COPY --from=build /app/target/parking_control-0.0.1-SNAPSHOT.jar /app/javaspringapirest.jar

# Variáveis de ambiente (opcional, para customizar as opções do Java)
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS

# Expondo a porta 8080, que é a porta padrão para aplicações Spring Boot
EXPOSE 8080

# Inicia a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "/app/javaspringapirest.jar"]
