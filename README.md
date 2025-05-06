# Sistema de Encomendas para Prédios Residenciais

🚀 **Tecnologias e Ferramentas Utilizadas**

Este projeto é um sistema desenvolvido em Java com Spring Boot, utiliza um banco de dados PostgreSQL para persistência e um serviço de envio de e-mails via Mailtrap, para gerenciamento de encomendas em prédios residenciais. As principais tecnologias e ferramentas utilizadas incluem:

* **Linguagem de Programação:** Java 17
* **Estrutura:** Spring Boot
* **Gerenciador de Build:** Maven
* **Persistência de Dados:** Spring Data JPA
* **Banco de Dados:** Postgres
* **Documentação da API:** Swagger (SpringDoc OpenAPI)
* **Autenticação e Autorização:** Spring Security, JWT
* **E-mail:** Remetente de e-mail Java (Java Mail Sender)
* **Mensageria (Opcional):** RabbitMQ
* **Testes:** JUnit, Mockito
* **IDE:** IntelliJ IDEA
* **Controle de versão:** Git
* **Conteinerização:** Docker (mencionado no objetivo)

📦 **Funcionalidades**

O sistema oferece as seguintes funcionalidades principais:

**Autenticação e Autorização:**

* A aplicação usa JWT (JSON Web Token) para autenticação. O segredo e o tempo de expiração do token estão configurados como:
  * Autenticação de usuários (moradores e funcionários) via JWT.
  * Controle de acesso baseado em funções (ADMIN, PORTEIRO, MORADOR).
  * **Expiração:** 24 horas (86400000 milissegundos). ⚠️ **Melhor prática:** Nunca exponha `jwt.secret` em arquivos públicos. Utilize variáveis de ambiente para segurança.

**Gerenciamento de Funcionários:**

* CRUD completo e Login para funcionários (Cadastro, Leitura, Atualização, Exclusão).

**Gestão de Moradores:**

* CRUD completo para moradores e login.

**Gerenciamento de Encomendas:**

* Cadastro de encomendas.
* Lista de encomendas pendentes de retirada.
* Busca de encomendas por ID.
* Busca de encomendas por morador destinatário.
* Confirmação de retirada de encomendas.
* Lista de encomendas retiradas.

**Notificações:**

* Notificação por e-mail no cadastro de uma nova encomenda.
* Notificação por e-mail na confirmação da retirada de uma encomenda.

🎯 **Objetivo**

Projeto acadêmico desenvolvido como parte dos estudos de Java e Spring Boot, com ênfase em:

* Aplicação dos princípios de Arquitetura Limpa.
* Qualidade de software.
* Utilização de conteinerização com Docker.
* Domínio de operações com banco de dados.
* Implementação de autenticação e autorização com JWT e Spring Security.
* Documentação da API com Swagger.
* Envio de e-mail com Java Mail Sender.
* (Opcional) Implementação de mensagens com RabbitMQ.

🛠️ **Configuração e Execução**

**Pré-requisitos**

* Java 17
* Maven
* Git
* Docker (para usar a imagem)
* (Opcional) Um servidor RabbitMQ, se você quiser usar os recursos de mensagens.

**Instruções**

1.  **Clone o repositório:**
    ```bash
    git clone <URL do seu repositório GitHub>
    cd encomendasum
    ```

2.  **Configurar o banco de dados:**
    A aplicação está configurada para usar PostgreSQL como banco de dados. Os detalhes de conexão estão definidos no `application.properties`.

3.  **Configurar o servidor de e-mail (Mailtrap):**
    Configuração do Envio de E-mails. A aplicação usa Mailtrap para envio de e-mails. Aqui estão os detalhes da configuração SMTP:
    O aplicativo está configurado para usar o Mailtrap para envio de e-mails de desenvolvimento.

    ```properties
    spring.mail.host=sandbox.smtp.mailtrap.io
    spring.mail.port=587
    spring.mail.username=<SEU_USERNAME_MAILTRAP>
    spring.mail.password=<SUA_SENHA_MAILTRAP>
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    ```

    Crie uma conta gratuita do Mailtrap em <https://mailtrap.io/> para obter suas próprias credenciais. Atualize o arquivo `application.properties` com suas credenciais.

4.  **Compilar e executar a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```

5.  **Acesse o Swagger UI:**
    A documentação da API estará disponível em: `http://localhost:8080/swagger-ui.html`

**Execução com Docker (opcional)**

1.  **Execute o contêiner Docker:**
    No terminal do IntelliJ:
    ```bash
    docker-compose up -d
    ```

2.  O aplicativo estará acessível em `http://localhost:8080`
3.  A interface do usuário do Swagger estará em `http://localhost:8080/swagger-ui.html`

📁 **Estrutura do Projeto**

src/main/java: Código fonte da aplicação.

├── adapters: Camada de adaptadores (controladores e gateways).

├── domain: Camada de domínio (entidades e regras de negócio).

├── infrastructure: Camada de infraestrutura (persistência, serviços externos).

├── usecase: Camada de casos de uso (serviços).

└── EncomendasApplication.java: Ponto de entrada da aplicação Spring Boot.

src/main/resources: Arquivos de configuração e recursos.

└── application.properties: Configuração do Spring Boot.

src/test/java: Testes unitários e de integração.

pom.xml: Arquivo de configuração do Maven.

README.md: Documentação do projeto.

⚠️ **Desafios Técnicos e Soluções Adotadas**

**Gerenciamento de Dependências (`pom.xml`):**

* **Desafio:** Sincronizar as versões das dependências no `pom.xml` para evitar conflitos e garantir a compatibilidade entre as bibliotecas do Spring Boot e outras dependências.
* **Solução:**
  * Utilização do `spring-boot-starter-parent` para herdar o gerenciamento de versões do Spring Boot, garantindo a compatibilidade entre os módulos do Spring.
  * Especificação manual de versões para dependências não gerenciadas pelo `spring-boot-starter-parent`, como o JWT (`io.jsonwebtoken:jjwt`), e verificação da compatibilidade com a versão do Spring Boot.
  * Atualização do plugin `maven-compiler-plugin` para garantir a compatibilidade com a versão do Java utilizada (Java 17).
  * Utilização do Lombok.

**Tratamento de Exceções e Documentação Swagger:**

* **Desafio:** A tentativa de implementar um tratamento de abordagem global centralizado estava causando problemas na geração da documentação do Swagger. O Swagger não conseguiu interpretar corretamente as respostas de erro específicas, resultando em erros de documentação.
* **Solução:**
  * O tratamento de abordagens globais foi removido para resolver o problema de compatibilidade com o Swagger.
  * O tratamento de abordagens foi implementado de forma local, dentro de cada Controlador, utilizando o `try-catch` e `ResponseStatusException`. As respostas de erro são documentadas explicitamente em cada método do Controller com as anotações `@ApiResponse`, garantindo que o Swagger exiba as possíveis respostas de erro da API.
  * Foi investigado o uso de `@ExceptionHandler`, mas não foi possível resolver o problema ao longo do tempo.