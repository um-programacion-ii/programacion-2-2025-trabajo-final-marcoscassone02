[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/IEOUmR9z)

# 🎟️ Sistema de Venta de Entradas

Proyecto académico desarrollado como aplicación **cliente–servidor**, que permite:
- Iniciar sesión de usuarios
- Visualizar eventos
- Seleccionar y bloquear asientos
- Confirmar ventas
- Consultar historial y detalle de ventas

El sistema utiliza **Spring Boot** en el backend, **Redis** para la gestión del estado de sesión y una app **Android (Jetpack Compose)** como frontend.

Alumno: Marcos Cassone - 62336

## 🧱 Tecnologías utilizadas

- **Backend**
  - Java 17
  - Spring Boot
  - Redis
  - Maven

- **Frontend**
  - Android
  - Kotlin
  - Jetpack Compose
  - Ktor Client

- **Infraestructura**
  - Docker
  - Docker Compose

---

**Descargar Docker(linux)**

sudo apt update
sudo apt install -y docker.io docker-compose-plugin
sudo systemctl enable docker
sudo systemctl start docker

**Como levantar la App**

1 - Levantar Redis local
Desde la raíz del proyecto:

docker compose up -d

2 - Levantar Backend
Desde la carpeta del backend

mvn spring-boot:run

3 - Levantar Proxy
Desde la carpeta del Porxy

mvn spring-boot:run

4 - Levantar Front

Desde Android Studio ejecutar la aplicacion

**Caso de Fin de Sesion**

En el caso de que haya expirado el token de la catedra:

POST http://192.168.194.250:8080/api/authenticate
body:
{
  "username": "marcos",
  "password": "marcos",
  "rememberMe": false
}

Copiar la devolucion del id-token en el app.properties del backend y del proxy 
en la variable catedra.auth-token

**Crear Usuario Local**

curl -X POST http://localhost:8080/debug/usuarios \
  -H "Content-Type: application/json" \
  -d '{"username":"","password":""}'