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
