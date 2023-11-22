# Telegram chat bot

Api created for providing access to data, functionality, and services that enhance the customer experience, and drive business growth

## Table of Contents
- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [Summary](#summary)

## Introduction


Embark on a simple and smooth chat experience with our Telegram bot, designed to make your life more straightforward. Whether you need information, help, or just want a friendly chat, Chat Bot is ready to respond quickly and smartly.

## Features

#### 1. Chat Bot
You can ask anything in our chat, and it will give you a response.

#### 2. Functionalities for admin
Admin can watch history of messages, and send message to chats

#### 3. Security and JWT Tokens for User auth
Security is paramount in our application. We've implemented Spring Security to safeguard your data and transactions. JWT (JSON Web Tokens) are used for secure authentication and authorization, ensuring that only authorized users can access sensitive functionalities.

###  Technologies Used
Our application leverages several cutting-edge technologies, including:
- **Spring Data JPA:** For efficient data access and persistence.
- **Spring Security:** To secure the application and user data.
- **MapStruct:** For simplified and efficient mapping between DTOs and entities.
- **Docker:** Containerization for easy deployment and scalability.
- **OpenAPI**: For documenting our RESTful APIs, providing clear and detailed information about available resources, request payloads, and response formats.
  **Liquibase**: Used to manage and version the database schema, allowing for easy database schema changes and ensuring data schema consistency across different environments.

  
## Prerequisites

List the prerequisites required to run your application. Include things like:

- Java 17 (https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Docker (https://www.docker.com/)
- PostgreSQL (https://www.postgresql.org/)

### Installation

1. Firstly you need to clone repository <br>

clone from console with git clone

2. Build project and download dependencies for Maven with command:
 ```bash 
mvn clean install
```
3. Create your .env file and write there key properties:
DATASOURCE_URL=
DATASOURCE_USERNAME=
DATASOURCE_PASSWORD=
JWT_SECRET=
OPENAPI_KEY=


### Usage

**For access to all endpoints use admin credentials in auth/login** <br>
**Username**: romakuch@gmail.com <br>
**Password**: romaaaa
http://ec2-52-205-209-190.compute-1.amazonaws.com/swagger-ui/index.html#/

**Create your own admin:**
Use endpoint auth/register to register user and stick to these rules:

1.firstName length between 1 and 255<br>
2.lastName length between 1 and 255<br>
3.email should be valid<br>
4.password minimum length is 4<br>

**For access to all endpoints use admin credentials in auth/login** <br>
**Username**: romakuch@gmail.com <br>
**Password**: romaaaa

### Contributing
We welcome contributions from the community to enhance the Telegram Chat Bot. Whether you want to fix a bug, improve an existing feature, or propose a new feature, your contributions are valuable to us. You can follow [Installation](#installation) guide and create Pull Request to project.
If you want to contact for more information and cooperation in development: romakuchmii@gmail.com


### Summary
In summary, our project overcame these challenges through adaptable data modeling, strong security measures, systematic error handling.
It stands as a testament to our commitment to best practices in software development, offering users a secure and enjoyable shopping experience while adhering to REST principles.
