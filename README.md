# Cuik Foods

Sistema de gestion para restaurantes desarrollado para la materia Programacion
Avanzada. La idea es cubrir la administracion del menu, mesas, empleados y
pedidos, ademas de permitir que los clientes pidan y paguen desde la mesa.

El repositorio contiene un backend en Java, una SPA de administracion en Preact
y las migraciones de MySQL.

## Estado actual

### Implementado

- Backend propio sobre Jetty con un mini framework de controladores basado en
  anotaciones (`@Controller`, `@Get`, `@Post`, `@Put`, `@Delete`), inyeccion de
  dependencias y ruteo por Prefix Tree (trie).
- Persistencia en MySQL mediante repositorios, mapeo automatico de resultados y
  migraciones SQL en `migrations/`.
- Servidor de archivos estaticos, usado para publicar la SPA compilada desde
  `public/`.
- Autenticacion:
  - Registro con contraseña hasheada usando Argon2.
  - Login con verificacion de contraseña y generacion de JWT.
- ABM de usuarios:
  - Listado, consulta por id, creacion, actualizacion y eliminacion.
  - La creacion exige contraseña; la actualizacion permite cambiarla u omitirla.
  - Pantalla de usuarios en el frontend con alta, edicion, eliminacion y tabla
    con scroll.
- Consulta de menus desde el backend:
  - Listado de menus.
  - Detalle de un menu con sus items.
  - Busqueda de items por nombre, categoria o descripcion.
- Frontend de administracion con Preact, TypeScript, Vite y ruteo por hash.
- Datos de ejemplo y migracion inicial con usuarios, menus, items, alérgenos y
  datos nutricionales.

### En progreso o pendiente

- ABM de menus e items desde la interfaz: actualmente el backend solo expone
  lectura y busqueda.
- Pantallas de empleados, mesas, menus, transacciones y compras: hoy son
  placeholders en el frontend.
- Gestion de mesas, asignacion de mozos y seguimiento de pedidos.
- Flujo de pedido del cliente mediante QR.
- Pagos, propinas y division de gastos.
- Integracion del login en el frontend y proteccion de rutas.

## Stack

- Java 21
- Gradle
- Jetty, Gson, MySQL Connector/J
- Argon2 y JWT
- MySQL
- Preact, TypeScript, Vite y Wouter

## Estructura

```text
cuik-server/    Backend Java y build de Gradle
cuik-web/       SPA en Preact
migrations/     Esquema inicial y datos de ejemplo
public/         Build del frontend, servido por el backend
docs/           Documentacion de la materia
config.json     Configuracion de base de datos, JWT y archivos estaticos
Makefile        Tareas de build y ejecucion
```

## Requisitos

- JDK 21
- Gradle
- Node.js y npm
- Vite disponible como comando global si se usa el `Makefile`
- Docker y Docker Compose, o una instancia local de MySQL
- `make` si se quieren usar los atajos del `Makefile`

## Como ejecutar

### Con Make

Instalar las dependencias del frontend una vez:

```bash
cd cuik-web
npm install
cd ..
```

Levantar la base de datos:

```bash
make db
```

Compilar backend y frontend:

```bash
make build
```

Ejecutar el servidor:

```bash
make run-server
```

La aplicacion queda disponible en:

```text
http://localhost:8080
```

Otros comandos utiles:

```bash
make watch-web    # recompila el frontend al detectar cambios
make db-client    # abre el cliente de MySQL del contenedor
make clean        # limpia el build del servidor y baja la base
make clean-db     # baja la base de datos
```

El `Makefile` usa los comandos globales `gradle` y `vite`. Si no estan
instalados, usar la alternativa sin Make.

### Sin Make

#### 1. Base de datos

Con Docker Compose:

```bash
docker compose up -d db
```

Las migraciones de `migrations/` se ejecutan automaticamente la primera vez que
se inicializa el volumen de MySQL.

Si se usa un MySQL local, aplicar las migraciones manualmente:

```bash
mysql -u root -p < migrations/0001_InitialMigration.sql
mysql -u root -p < migrations/9999_DemoData.sql
```

#### 2. Backend

```bash
cd cuik-server
./gradlew shadowJar
cd ..
java -jar cuik-server/cuik.jar
```

El servidor escucha en `http://localhost:8080`. Debe ejecutarse desde la raiz
del repositorio para que encuentre `config.json` y sirva los archivos desde
`./public`.

También se puede indicar otro archivo de configuracion:

```bash
java -DCONFIG_FILE=/ruta/a/config.json -jar cuik-server/cuik.jar
```

#### 3. Frontend

Para generar el build que sirve el backend:

```bash
cd cuik-web
npm install
npx vite build --emptyOutDir
```

El resultado se escribe en `../public` segun `vite.config.ts`.

Para desarrollo con servidor de Vite:

```bash
cd cuik-web
npx vite --port 8081
```

En ese caso el frontend queda en `http://localhost:8081` y las llamadas a `/api`
se redirigen al backend en `http://localhost:8080`.

## Configuracion

La configuracion principal esta en `config.json`:

```json
{
    "DB_CONNECTION_STRING": "jdbc:mysql://localhost:3306/cuik?useUnicode=true&characterEncoding=utf-8",
    "DB_USER": "root",
    "DB_PWD": "root",
    "JWT_SECRET": "es.muy.secreto",
    "WWW_DIR": "./public"
}
```

Las migraciones solo se aplican sobre una base nueva. Para reiniciar la base
desde cero con Docker:

```bash
docker compose down -v
docker compose up -d db
```

## API principal

```text
POST   /api/auth/register      { name, email, password }
POST   /api/auth/login         { name, password }

GET    /api/users
GET    /api/users/{id}
POST   /api/users              { name, email, password }
PUT    /api/users              { id, name, email, password? }
DELETE /api/users/{id}

GET    /api/menus
GET    /api/menus/{id}
GET    /api/menus/search?query=...
```

TODOs:

- Investigar fuzzy search en mysql, ordenar por relevancia
