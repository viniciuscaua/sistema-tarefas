# Gerenciador de Tarefas

Esse é um projeto de gerenciamento de tarefas desenvolvido em Java com Spring Boot, utilizando tecnologias modernas e seguindo as melhores práticas de desenvolvimento.

## Tecnologias Principais

- Spring Boot
- JavaServer Faces (JSF)
- Spring Data JPA
- PostgreSQL
- JUnit 5 & Mockito

## Funcionalidades Implementadas

O projeto atende aos seguintes requisitos:

a) ✅ Aplicação Java Web utilizando JavaServer Faces (JSF)
   - Interface de usuário implementada com JSF e PrimeFaces
   - Componentes ricos e responsivos do PrimeFaces para melhor experiência do usuário
   - Página XHTML para interação com o usuário
   - Managed Beans para controle do fluxo da aplicação

b) ✅ Persistência em banco de dados PostgreSQL
   - Configuração do banco de dados via application.properties
   - Gerenciamento de conexões com o banco de dados

c) ✅ Utilização de JPA
   - Entidades mapeadas com anotações JPA
   - Uso do Spring Data JPA para operações com o banco de dados
   - Repositórios para acesso aos dados

d) ✅ Testes de unidade
   - Testes unitários implementados com JUnit 5
   - Uso do Mockito para simulação de dependências
   - Cobertura de testes para as principais funcionalidades

## Pré-requisitos

Para executar o projeto, você precisará ter instalado:

- Java 17 ou superior
- Maven
- PostgreSQL
- IDE de sua preferência
- Spring Boot 3.x (incluído nas dependências Maven)

## Configuração do Ambiente

1. Clone o repositório:
```bash
git clone [URL_DO_REPOSITORIO]
```

2. Configure o banco de dados PostgreSQL:
   - Crie um banco de dados chamado "tarefas_bd"
   - Atualize as credenciais no arquivo `src/main/resources/application.properties`:
     ```properties
     spring.datasource.username=seu_usuario_postgres
     spring.datasource.password=sua_senha_postgres
     ```

3. No diretório do projeto, execute:
```bash
mvn clean install
```

## Executando o Projeto

1. Para iniciar a aplicação, execute:
```bash
mvn spring-boot:run
```

2. Acesse a aplicação em seu navegador:
```
http://localhost:8080
```

## Executando os Testes

Para executar os testes unitários do projeto, utilize o comando:
```bash
mvn test
```

## Estrutura do Projeto

- `src/main/java/com/example/taskmanager/`
  - `controller/`: Controlador da aplicação
  - `model/`: Entidade e modelo
  - `repository/`: Repositórios JPA
  - `service/`: Camada de serviços

- `src/test/`: Testes unitários
- `src/main/resources/`: Arquivos de configuração e templates

## Funcionalidades do Sistema

- Cadastro de tarefas com título, descrição, responsável, prioridade e deadline
- Listagem de todas as tarefas
- Filtro de tarefas por diferentes critérios
- Atualização do status das tarefas
- Exclusão de tarefas 