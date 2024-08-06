# StoreAPI

# Axion-Release Plugin

Axion-Release es un plugin para Gradle que facilita la gestión y creación de versiones en proyectos. En lugar de leer la versión del proyecto desde un archivo de construcción (como `build.gradle`), Axion-Release deriva la versión del tag más cercano en el sistema de control de versiones (SCM). Si el commit actual está etiquetado, el proyecto tiene una versión de lanzamiento. Si hubo commits después del último tag, el proyecto está en una versión SNAPSHOT.

## Generar tags y releases
**Etiquetar el commit con una versión de lanzamiento**:

    ```sh
    git tag v1.0.0
    ```
**Verificar la versión actual**:

    ```sh
    ./gradlew currentVersion
    ```
   La salida será `1.0.0` porque el commit actual está etiquetado como `v1.0.0`.

**Realizar un lanzamiento**:

    ```sh
    ./gradlew release
    ```
   Como el commit actual está etiquetado, el proyecto tiene una versión de lanzamiento `1.0.0`.

## Ejemplo: Commits después del último tag (Versión SNAPSHOT)

**Realizar más commits**:

    ```sh
    echo "More changes" >> example.txt
    git add example.txt
    git commit -m "Added more changes"
    ```

**Verificar la versión actual nuevamente**:

    ```sh
    ./gradlew currentVersion
    ```
   La salida será `1.0.1-SNAPSHOT` porque hay commits adicionales después del último tag `v1.0.0`.

**Realizar un lanzamiento**:

    ```sh
    ./gradlew release
    ```
    - El plugin incrementará la versión basada en la configuración predeterminada (por ejemplo, `patch`) y creará un nuevo tag `v1.0.1`.
    - El `build.gradle` será actualizado automáticamente a `1.0.1`.

# Guía para construir y ejecutar una imagen docker

## Paso 1: Construir la Imagen Docker

Desde el directorio raíz del proyecto, construir la imagen Docker usando el siguiente comando:

```bash
docker build -t store-app:latest .
```
## Paso 2: Ejecutar la imagen docker

Una vez que la imagen esté construida, ejecutar un contenedor basado en esta imagen con el siguiente comando:
```bash
docker run -d -p 8081:8081 store-app:latest
```
# Levantar un contenedor con la imagen de docker hub
Ejecuta el siguiente comando para levantar un contenedor usando la imagen `gapatmej/store-api`:

```sh
docker run -d -p 8081:8081 --name store-api gapatmej/store-api:latest
```

# Uso de API

Esta API permite la gestión de productos con varios endpoints para crear, actualizar, eliminar y consultar productos.

## Endpoints

### 1. Autenticación

**Endpoint:** `/api/authenticate`

**Method:** `POST`

**Request Body:**
```json
{
  "username": "admin",
  "password": "admin"
}
```
**Response:**

- `200 OK` - Autenticado
- `401 Unauthorized` - Credenciales no válidas

**Example JSON Response:**
```json
{
  "id_token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTcyMjk5NDMxNn0._--N0xuAUKuTMZxuRnIb2gDL9M8MRo1FbQnzSK5PVpt7W0VvMmDCEFEoDf_Ch9yngMEC7EDQBso7aZhcuYRFDw"
}
```
### 2. Create Product

**Endpoint:** `/api/products`

**Method:** `POST`

**Request Body:**
```json
{
  "mainCode": "017",
  "productCategory": "ELECTRONICS",
  "name": "Producto 1 con accesorios",
  "price": 10,
  "attribute1": "Con mantenimiento",
  "attribute2": "",
  "active": true
}
```
**Response:**

- `201 Created` - Producto creado
- `401 Unauthorized` - Credenciales no válidas
- `400 Bad Request` - Request inválido

**Example JSON Response:**
```json
{
  "id": 1001,
  "mainCode": "017",
  "productCategory": "ELECTRONICS",
  "name": "Producto 1 con accesorios",
  "price": 10,
  "attribute1": "Con mantenimiento",
  "attribute2": "",
  "active": true
}
```
### 3. Update Product

**Endpoint:** `/api/products`

**Method:** `PUT`

**Request Body:**
```json
{
  "id": "1001",
  "mainCode": "017",
  "productCategory": "ELECTRONICS",
  "name": "Producto 1 con accesorios",
  "price": 10,
  "attribute1": "Con mantenimiento",
  "attribute2": "",
  "active": false
}
```
**Response:**

- `200 OK` - Producto actualizado
- `401 Unauthorized` - Credenciales no válidas
- `400 Bad Request` - Request inválido

**Example JSON Response:**
```json
{
  "id": "1001",
  "mainCode": "017",
  "productCategory": "ELECTRONICS",
  "name": "Producto 1 con accesorios",
  "price": 10,
  "attribute1": "Con mantenimiento",
  "attribute2": "",
  "active": false
}
```
### 4. Delete Product

**Endpoint:** `/api/products/{id}`

**Method:** `DELETE`
**Response:**

- `204 No Content` - Exito sin contenido
- `401 Unauthorized` - Credenciales no válidas
- `400 Bad Request` - Id inválido

### 5. Get All Products

**Endpoint:** `/api/products`

**Method:** `GET`

**Parameters:**

- `search` (string): Parámetro de búsqueda personalizada.
- `page` (integer): Número de página para paginación.
- `size` (integer): Tamaño de la página para paginación.

**Search Parameter Condicionales:**

- `GREATER_THAN (>)`
- `GREATER_OR_EQUAL (>=)`
- `LESS_THAN (<)`
- `LESS_OR_EQUAL (<=)`
- `EQUAL (=)`
- `NOT_EQUAL (!=)`
- `LIKE (:)`
- `IN ([])`
- `NOT_IN (![])`
- `NOT_DATA (<>)`
- `IN_SEPARATOR (;)`
- `KEY_SEPARATOR (\\.)`
- `OR (*)`

**Example Request:**
```http
GET /products?search=id[]1001*name:con accesorios&page=0&size=5
```
**Response:**

- `200 OK` - Consulta ejecutada con éxito
- `401 Unauthorized` - Credenciales no válidas

**Example JSON Response:**
```json
[
  {
    "id": 1001,
    "mainCode": "021",
    "auxiliaryCode": null,
    "barcode": null,
    "productCategory": "CLOTHING",
    "name": "Producto 1 con accesorios",
    "price": 10.0000,
    "attribute1": "Con mantenimiento",
    "attribute2": "",
    "active": true
  }
]
```
### 6. Get Product by ID

**Endpoint:** `/products/{id}`

**Method:** `GET`

**Response:**

- `200 OK` - Producto encontrado
- `401 Unauthorized` - Credenciales no válidas
- `404 Not Found` - Producto no encontrado
- `400 Bad Request` - Id inválido
- 
**Example JSON Response:**
```json
{
    "id": "1001",
    "mainCode": "011",
    "auxiliaryCode": "011",
    "barcode": "011",
    "name": "Producto 1 con accesorios",
    "price": 10,
    "attribute1": "Con mantenimiento",
    "attribute2": "",
    "active": true
}
```
# Uso del Sistema de Métricas de Categorías de Producto

Este documento explica cómo utilizar el sistema de métricas para categorías de productos implementado en la aplicación.

## Acceso a las Métricas

Una vez que el sistema esté en funcionamiento, puedes acceder a las métricas a través de los siguientes endpoints:

### Endpoint de Prometheus

Visita [http://localhost:8081/actuator/prometheus](http://localhost:8080/actuator/prometheus) para ver todas las métricas en formato compatible con Prometheus.

### Endpoint de Métricas de Actuator

Visita [http://localhost:8081/actuator/metrics](http://localhost:8080/actuator/metrics) para ver un resumen de todas las métricas expuestas.

### Métricas Específicas

Para ver métricas específicas para una categoría, utiliza la URL:

```http://localhost:8080/actuator/metrics/product_category_<category>```

Reemplaza `<category>` con el nombre en minúsculas de la métrica deseada, como `product_category_electronics`.
## Categorías de Producto

El `enum` `ProductCategoryEnum` se define con las siguientes categorías de producto:

```java
public enum ProductCategoryEnum {
    ELECTRONICS("Electronics"),
    CLOTHING("Clothing"),
    BOOKS("Books"),
    TOYS("Toys"),
    BEAUTY("Beauty"),
    SPORTS("Sports");
}
```