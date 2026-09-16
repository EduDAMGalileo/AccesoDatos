# Acceso a Datos · 2º DAM

Repositorio con los **ejercicios y ejemplos** del módulo profesional de **Acceso a Datos**, del ciclo formativo de grado superior de Desarrollo de Aplicaciones Multiplataforma (DAM).

El objetivo es tener en un mismo sitio el código que vemos en clase, para poder consultarlo, probarlo y usarlo como punto de partida en las prácticas.

## Contenidos

Los ejemplos siguen, a grandes rasgos, los bloques del módulo:

- **Ficheros**: lectura y escritura de ficheros de texto y binarios, acceso aleatorio, serialización de objetos y tratamiento de XML y JSON.
- **Bases de datos relacionales**: conexión con JDBC, consultas, sentencias preparadas, transacciones y procedimientos almacenados.
- **Mapeo objeto-relacional (ORM)**: persistencia de objetos con JPA / Hibernate.
- **Bases de datos NoSQL y orientadas a objetos**: almacenamiento y consulta de documentos.
- **Componentes de acceso a datos**: patrón DAO y organización del código de persistencia.

## Requisitos

- JDK 25
- Eclipse IDE for Java Developers
- Un gestor de bases de datos para los ejemplos de JDBC concretamente PostgreSQL
- Los drivers o librerías necesarios, indicados en cada ejercicio

## Cómo usar el repositorio

1. Clona el repositorio:

   ```bash
   git clone <url-del-repositorio>
   ```

2. En Eclipse, ve a **File → Import → General → Projects from Folder or Archive** y selecciona la carpeta clonada.

3. Si un ejercicio usa librerías externas (por ejemplo, el conector JDBC), añádelas al *Build Path*: clic derecho en el proyecto → **Build Path → Configure Build Path → Libraries**.

> La configuración de Eclipse (`.project`, `.classpath`, `.settings/`) no se sube al repositorio, así que puede que tengas que ajustar la versión de Java o las librerías la primera vez que importes un proyecto.

## Aviso

Los ejemplos tienen fines **didácticos**: priorizan que el código se entienda por encima de que esté optimizado o listo para producción. Las credenciales de bases de datos que aparecen son de prueba y solo sirven en local.
