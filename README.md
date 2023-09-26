# Pokémons

## Descripción
Programa desarrollado en Java con Gradle que simula una Pokédex con datos de Pokémon.
El programa lee un fichero de datos de Pokémon de tipo JSON. Tras leerlo, se realizan consultas utilizando la API Stream obteniendo los siguientes datos:

    - Obtener el nombre los 10 primeros Pokémon.
    - Obtener el nombre de los 5 últimos Pokémon.
    - Obtener los datos de Pikachu.
    - Obtener la evolución de Charmander.
    - Obtener el nombre de los Pokémon de tipo Fire.
    - Obtener el nombre de los Pokémon con debilidad Water o Electric.
    - Número de Pokémon con solo una debilidad.
    - Pokémon con más debilidades.
    - Pokémon con menos evoluciones.
    - Pokémon con una evolución que no es de tipo fire.
    - Pokémon más pesado.
    - Pokémon más alto.
    - Pokémon con el nombre mas largo.
    - Media de peso de los Pokémon.
    - Media de altura de los Pokémon.
    - Media de evoluciones de los Pokémon.
    - Media de debilidades de los Pokémon.
    - Los Pokémon agrupados por tipo.
    - Número de Pokémon agrupados por debilidad.
    - Los Pokémon agrupados por número de evoluciones.
    - La debilidad más común.

A continuación, se exportarán los siguientes datos de Pokémon a un CSV en la carpeta /out:

    id, número, nombre, altura y peso

Se leerá el fichero exportado y los datos se pasarán al Logger.

Una vez leídos los datos, estos serán exportados a un CSV de una base de datos H2.

Finalmente, se obtendrá la información de Pikachu.

## 💡 Instrucciones de uso
- ⚠ **.env:** Este fichero se deberá de crear en la carpeta resources con los siguientes datos:

        DATABASE_USER=usuario
        DATABASE_PASSWORD=contraseña
    Deberás de modificar el usuario y la contraseña que quieres que tenga la base de datos. La razón por la que el .env no se agrega al repositorio es por motivos de seguridad. Estos datos están aislados del database.properties.

- **database.properties:** Este fichero es el que se deberá modificar si se quiere cambiar la URL, el driver, el nombre de la base de datos o si se quiere forzar el reinicio de la tabla Pokémon en el inicio del programa (eliminará y volverá a crear la tabla de Pokémon).

## ⚙ Tecnologías
- Lombok
- H2 Database
- dotenv-kotlin
- Gson
- OpenCSV
- MyBatis
- Logback Classic

## Estructura
- Controller: El controlador de Pokémon. Aquí se realizan las consultas API Stream de los Pokémon y también las consultas a la base de datos.
- DTO: Aquí se encuentra el DTO con los datos de Pokémon. Se utiliza para tratar los datos necesarios.
- Exceptions: Se almacenan todas las excepciones personalizadas del programa.
- Models: Clases POJO.
- Repositories: El manejo de consultas CRUD para interactuar con la base de datos.
- Services: Servicios de la aplicación.
  - Database: Administrar la base de datos y sus conexiones.
  - IO: Entrada y salida de datos. el CSVManager administra el importado y el exportado de los datos de Pokémon en CSV.
- Utils: Clases de utilidad. StringConverters tiene los parsers necesarios para convertir datos de String a otros tipos de datos. LogGeneral es el logger general de la aplicación. Y ApplicationProperties la clase que se encarga de cargar los archivos de propiedades (en este programa, database.properties).
- Main: Clase principal del programa.
- PokemonProgram: La clase que se encarga de ejecutar el programa de Pokémon. Se le llama en el Main de la aplicación.