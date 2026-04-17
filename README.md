# AML Cordova - Backend API 🛡️

## Descripción
Este es el núcleo de servicios (Backend) para el sistema **AML Cordova**, una plataforma especializada en el cumplimiento normativo y la prevención de lavado de activos (Anti-Money Laundering). La API está construida bajo una arquitectura escalable, encargándose del procesamiento de reglas de cumplimiento, gestión de auditorías y monitoreo de transacciones.

## 🚀 Tecnologías Principales
* **Lenguaje:** Java 21
* **Framework:** Spring Boot 4.0.5 / (Core, Security, Data JPA)
* **Base de Datos:** Oracle Database
* **Seguridad:** Spring Security con JWT (JSON Web Tokens)
* **Documentación:** Swagger / OpenAPI 3
* **Gestor de Dependencias:** Maven
* **Contenedores:** Docker

## 🏗️ Arquitectura
El proyecto sigue un patrón de **Arquitectura Multicapa** para garantizar la separación de responsabilidades:
* **Controller:** Puntos de entrada REST.
* **Service:** Lógica de negocio y reglas de cumplimiento AML.
* **Repository:** Capa de abstracción de datos (Spring Data JPA).
* **Security:** Configuraciones de autenticación y autorización.
* **Models/DTOs:** Definición de entidades y objetos de transferencia de datos.

## 📋 Requisitos Previos
* JDK 21 o superior.
* Maven 3.8+.
* Instancia de Base de Datos (Oracle) activa.
* Docker (opcional, para despliegue).

## 🔧 Configuración e Instalación

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/tu-usuario/aml-cordova-backend.git](https://github.com/tu-usuario/aml-cordova-backend.git)
   cd aml-cordova-backend
