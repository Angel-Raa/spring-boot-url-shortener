# Shortener Url 

Una aplicación de acortamiento de URL desarrollada en Spring Boot, que genera URLs cortas únicas para redirigir a las originales. Incluye autenticación y seguridad para proteger las URLs acortadas

Si encuentras útil este repositorio, ¡por favor ayúdanos marcándolo con una ⭐! 😊

## Tecnologías Utilizadas

- JDK 21
- Spring Boot 3
- Spring Data Jpa
- Spring security 6
- PostgreSQL 15
- Java JWT
- Docker

## Prerrequisitos

Asegúrate de tener instalados los siguientes componentes en tu entorno de desarrollo antes de comenzar:

1. [Git](https://git-scm.com/downloads)
2. [Docker](https://docs.docker.com/compose/install/)

## Configuración del Entorno 

#### Clonar el repositorio en tu máquina local

```
git clone https://github.com/Angel-Raa/spring-boot-url-shortener.git
```

#### Navegar al directorio del proyecto
Dirígete al directorio del proyecto recién clonado utilizando el siguiente comando:

```
cd spring-boot-url-shortener
```

#### Crear y Configurar .env y resources/application-dev.yaml


**.env** 
Este archivo contiene las variables de entorno para la aplicación.
```bash
POSTGRES_USER=shortener
POSTGRES_DB=shortener
POSTGRES_PASSWORD=admin
```
**application-dev.yaml** 
Este archivo contiene las credenciales para la base de datos.
```bash
SECRET_JWT: secret
POSTGRES_USER: shortener
POSTGRES_DB: shortener
POSTGRES_PASSWORD: admin
HOST: localhost
POST: 5432
CORS_ENABLED: true
```

#### Construir y ejecutar los contenedores de Docker Compose
```
docker compose up -d
```
### Documentación de los Endpoints

### Base URL

- [http://localhost:2020](http://localhost:8080)

Se requiere autenticación para acceder a los endpoints de esta API. Puedes obtener un token JWT mediante los siguientes endpoints:

## Autenticación

| Método   | Endpoint                               | Descripción                                |
|----------|----------------------------------------|--------------------------------------------|
| POST     | `/api/authentication/login`            | Iniciar sesión de usuario                  |
| POST     | `/api/authentication/register`         | Registro de usuario                        |
| POST     | `/api/authentication/logout`           | Cerrar sesión de usuario                   |
| POST     | `/api/authentication/refresh`          | Refrescar token de acceso                  |

- **Iniciar sesión de usuario:** `POST /authentication/login`
    - Cuerpo de la solicitud:
        ```json
        {
            "username": "alba",
            "password": "alba"
        }
        ```
    - Respuesta exitosa:
        ```json
         {
            "message": "Registration successful",
            "status": "OK",
            "code": 200,
            "error": false,
            "timestamp": "27-05-2024 17:25:48",
            "data": {
              "accessToken": "",
              "refreshToken": ""
              }
          }
        ```
  - **Registro de usuario:** `POST /authentication/register`
    - Cuerpo de la solicitud:
        ```json
        {
            "username": "angel",
            "password": "admin",
            "email":"alba@gmail.com"
        }
        ```
    - Respuesta exitosa:
        ```json
           {
              "message": "Authentication successful",
              "status": "OK",
              "code": 200,
              "error": false,
              "timestamp": "27-05-2024 17:25:48",
                "data": {
                  "accessToken": "",
                  "refreshToken": ""
                }
            }
        ```
## Recursos Adicionales

Aquí hay algunos recursos adicionales que podrían ser útiles:
- [Documentacion de Spring Data](https://spring.io/projects/spring-data)
- [Documentación de Java JWT](https://github.com/jwtk/jjwt)
- [Documentacion de open jdk 17](https://docs.oracle.com/en/java/javase/17/docs/api/)
- [Documentacion de Spring boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Documentacion de Maven](https://maven.apache.org/guides/getting-started/)
- [Documentacion de Docker](https://docs.docker.com/)
- [Documentacion de Spring Security](https://docs.spring.io/spring-security/reference/index.html)
- [Documentacion de Git](https://git-scm.com/doc)
