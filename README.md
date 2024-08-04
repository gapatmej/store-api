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

# Uso de API

Esta API permite la gestión de productos con varios endpoints para crear, actualizar, eliminar y consultar productos.

## Endpoints

### 1. Create Product

**Endpoint:** `/products`

**Method:** `POST`

**Request Body:**
```json
{
    "mainCode": "011",
    "auxiliaryCode": "011",
    "barcode": "011",
    "name": "Producto 1 con accesorios",
    "price": 10,
    "attribute1": "con todo",
    "attribute2": "",
    "active": true
}
```
### 2. Update Product

**Endpoint:** `/products`

**Method:** `PUT`

**Request Body:**
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
### 3. Delete Product

**Endpoint:** `/products/{id}`

**Method:** `DELETE`

### 4. Get All Products

**Endpoint:** `/products`

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

**Response:**

- `200 OK` - Lista de productos

**Example Request:**
```http
GET /products?search=id[]1001*name:con accesorios&page=0&size=5
```

### 5. Get Product by ID

**Endpoint:** `/products/{id}`

**Method:** `GET`

**Response:**

- `200 OK` - Producto encontrado
- `404 Not Found` - Producto no encontrado

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


