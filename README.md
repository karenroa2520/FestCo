# FestCo - Sistema de Gestión de Eventos

Sistema web desarrollado en Spring Boot para la gestión de eventos, boletas y artistas.

## Tecnologías Utilizadas

- **Backend**: Spring Boot 3.5.6
- **Base de Datos**: MySQL 8.0
- **Frontend**: HTML5, CSS3, JavaScript, Thymeleaf
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven
- **Java Version**: 21

## Dependencias Principales

```xml
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-thymeleaf
- mysql-connector-j
- lombok
- spring-boot-devtools
```

## Instrucciones de Ejecución

### Prerrequisitos

1. **Java 21** instalado
2. **Maven** instalado
3. **MySQL 8.0** instalado y ejecutándose
4. **Base de datos** creada con el nombre `ferias`

### Configuración de la Base de Datos

1. Crear una base de datos MySQL llamada `ferias`:
```sql
CREATE DATABASE ferias;
```

2. Configurar las credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=tu_password_aqui
```

3. Ejecutar el script SQL `DataBase/FestCo.sql` para crear las tablas y datos iniciales.

### Ejecución del Proyecto

1. **Clonar o descargar** el proyecto
2. **Abrir terminal** en la carpeta raíz del proyecto
3. **Ejecutar** el siguiente comando:

```bash
# En Windows
mvnw.cmd spring-boot:run

# En Linux/Mac
./mvnw spring-boot:run
```

4. **Acceder** a la aplicación en: `http://localhost:8080`

### Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/senasoft/ferias/
│   │   ├── Controller/          # Controladores REST y Web
│   │   ├── Entity/              # Entidades JPA
│   │   ├── Repository/           # Repositorios JPA
│   │   ├── service/              # Interfaces de servicios
│   │   ├── ServiceImpl/          # Implementaciones de servicios
│   │   └── FeriasApplication.java
│   └── resources/
│       ├── static/               # Archivos estáticos (CSS, JS, imágenes)
│       ├── templates/            # Plantillas Thymeleaf
│       └── application.properties
└── test/                         # Pruebas unitarias
```

## Funcionalidades Implementadas

### 1. Gestión de Eventos
- **Crear eventos** con información completa
- **Asociar localidades** con precios y cantidades
- **Gestionar artistas** participantes
- **Validación de horarios** para evitar conflictos

### 2. Sistema de Autenticación
- **Registro de usuarios** con validación
- **Inicio de sesión** funcional
- **Gestión de tipos de documento**

### 3. Interfaz de Usuario
- **Página principal** con eventos en formato card
- **Formulario de administrador** para crear eventos
- **Sistema de login/registro** responsive

### 4. API REST
- **Endpoints** para municipios por departamento
- **Gestión de artistas** y localidades
- **Integración** con frontend JavaScript

## Endpoints Principales

### Web Controllers
- `GET /` - Página principal con eventos
- `GET /login` - Página de login/registro
- `GET /administrador` - Panel de administración
- `POST /login` - Procesar login
- `POST /register` - Procesar registro
- `POST /eventos/completo` - Crear evento completo

### API REST
- `GET /api/municipios?departamentoId={id}` - Municipios por departamento
- `GET /api/artistas` - Lista de artistas
- `POST /api/artista` - Crear artista
- `GET /api/localidades` - Lista de localidades

## Base de Datos

### Entidades Principales
- **Evento**: Información del evento
- **Artista**: Artistas participantes
- **Localidad**: Zonas del evento
- **Cantidad_Boletas**: Precios y cantidades por localidad
- **Usuario**: Usuarios del sistema
- **Administrador**: Administradores
- **Municipio/Departamento**: Ubicaciones geográficas

## Solución de Problemas

### Error de Conexión a Base de Datos
1. Verificar que MySQL esté ejecutándose
2. Confirmar credenciales en `application.properties`
3. Verificar que la base de datos `ferias` existe

### Puerto en Uso
Si el puerto 8080 está ocupado, cambiar en `application.properties`:
```properties
server.port=8081
```

### Problemas de Compilación
1. Verificar que Java 21 esté instalado
2. Ejecutar `mvn clean install`
3. Verificar dependencias en `pom.xml`

## Desarrollo

### Agregar Nuevas Funcionalidades
1. Crear entidad en `Entity/`
2. Crear repositorio en `Repository/`
3. Implementar servicio en `ServiceImpl/`
4. Crear controlador en `Controller/`
5. Actualizar templates en `templates/`

### Estructura de Commits
- `feat:` Nueva funcionalidad
- `fix:` Corrección de bugs
- `docs:` Documentación
- `style:` Formato de código
- `refactor:` Refactorización

## Contacto

Proyecto desarrollado para SenaSoft - Curso de Spring Boot
