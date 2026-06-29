# Catalog Framework

Este projeto consiste em um framework genérico baseado em **Spring Boot** para o gerenciamento e venda de catálogos de itens com suporte a buscas dinâmicas por relevância e integração nativa com um assistente virtual baseado em Inteligência Artificial (Google Gemini).

Esta aplicação foi desenvolvida para a disciplina **DIM0162 - Engenharia de Software**. Trata-se da segunda parte do projeto prático de desenvolvimento de uma aplicação, focando agora em generalizar a base de código e transformá-la em um framework reutilizável.

## Como Executar

### Pré-requisitos
- Java 21 ou superior
- Maven 3.8+
- Chave da API do Google Gemini (`GEMINI_API_KEY`)

### Passo 1: 

Na raiz do projeto, execute o seguinte comando:
```bash
./mvnw clean install -DskipTests
```

### Passo 2:
O assistente inteligente exige uma chave válida de API para se comunicar com o Google Gemini. Defina a variável de ambiente:

**Linux / macOS:**
```bash
export GEMINI_API_KEY="sua-chave-api-aqui"
```

### Passo 3: Executar a Aplicação de Exemplo
Com a variável configurada e o framework compilado, execute a aplicação deseja, usando `smartstore` de exemplo:

```bash
./mvnw spring-boot:run -pl example/smartstore
```

A aplicação iniciará na porta **8080** Uma vez iniciada a aplicação de exemplo, você pode acessar a interface interativa do Swagger para testar os endpoints em [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)