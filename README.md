# Sistema de Encomendas para Prédios Residenciais

## 🚀 Tecnologias e Ferramentas Utilizadas

Este projeto é um sistema desenvolvido em Java com Spring Boot para gerenciar encomendas em prédios residenciais. As principais tecnologias e ferramentas utilizadas incluem:

* **Linguagem de Programação:** Java 17
* **Framework:** Spring Boot
* **Gerenciador de Build:** Maven
* **Persistência de Dados:** Spring Data JPA
* **Banco de Dados:** H2 (em memória - para desenvolvimento)
* **Documentação da API:** Swagger (SpringDoc OpenAPI)
* **Autenticação e Autorização:** Spring Security, JWT
* **E-mail:** Java Mail Sender
* **Mensageria (Opcional):** RabbitMQ
* **Testes:** JUnit, Mockito
* **IDE:** (Implicit, add if you have a preference, e.g., IntelliJ IDEA, Eclipse)
* **Version Control:** Git
* **Containerization:** Docker (mentioned in objective)

## 📦 Funcionalidades

O sistema oferece as seguintes funcionalidades principais:

* **Autenticação e Autorização:**
    * Autenticação de usuários (moradores e funcionários) via JWT.
    * Controle de acesso baseado em roles (ADMIN, PORTEIRO, MORADOR).
* **Gerenciamento de Funcionários:**
    * CRUD completo para funcionários (Cadastro, Leitura, Atualização, Exclusão).
* **Gerenciamento de Moradores:**
    * CRUD completo para moradores.
* **Gerenciamento de Encomendas:**
    * Cadastro de encomendas.
    * Listagem de encomendas pendentes de retirada.
    * Busca de encomendas por ID.
    * Busca de encomendas por morador destinatário.
    * Confirmação de retirada de encomendas.
    * Listagem de encomendas retiradas.
* **Notificações:**
    * Notificação por e-mail no cadastro de uma nova encomenda.
    * Notificação por e-mail na confirmação da retirada de uma encomenda.

## 🎯 Objetivo

Projeto acadêmico desenvolvido como parte dos estudos de Java e Spring Boot, com ênfase em:

* Aplicação dos princípios de Clean Architecture.
* Qualidade de software.
* Utilização de containerização com Docker.
* Domínio de operações com banco de dados.
* Implementação de autenticação e autorização com JWT e Spring Security.
* Documentação da API com Swagger.
* Envio de e-mails com Java Mail Sender.
* (Opcional) Implementação de mensageria com RabbitMQ.

## 🛠️ Configuração e Execução

### Pré-requisitos

* Java 17
* Maven
* Git
* Docker (se for utilizar a imagem)
* (Optional) A RabbitMQ server, if you want to use the messaging features.