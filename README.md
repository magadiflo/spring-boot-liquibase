# [Versionado de base de datos con Liquibase](https://www.linkedin.com/pulse/java-database-versioning-liquibase-tiago-melo/)

- [Liquibase Documentation](https://docs.liquibase.com/home.html)

---

## Introducción a Liquibase

El control de versiones de los cambios en la base de datos es tan importante como el control de versiones del código
fuente. Al utilizar una herramienta de migración de bases de datos, podemos gestionar de forma segura cómo evoluciona la
base de datos, en lugar de ejecutar un montón de archivos SQL sueltos sin versiones.

`Liquibase` es una solución de gestión de cambios de esquema de base de datos que le permite revisar y publicar cambios
en la base de datos de forma más rápida y segura desde el desarrollo hasta la producción.

Para comenzar a utilizar Liquibase de forma rápida y sencilla, puede escribir sus scripts de migración en `SQL`.

**Para aprovechar las capacidades de abstracción de la base de datos que le permiten escribir cambios una vez e
implementarlos en diferentes plataformas de bases de datos, puede especificar cambios independientes de la base de
datos en** `XML`, `JSON` o `YAML`.

## Características de Liquibase

- Es independiente de la base de datos: funciona con todos los principales proveedores de bases de datos.
- Puede especificar sus cambios en formatos `XML`, `YAML`, `JSON` y `SQL`.

Usaremos el formato `YAML`

## [ChangeLog](https://docs.liquibase.com/concepts/changelogs/home.html)

Un archivo que realiza un seguimiento de todos los cambios que deben ejecutarse para actualizar la base de datos.

Con `Liquibase`, utiliza un archivo de registro de cambios `(changeLog)` basado en texto para enumerar secuencialmente
todos los cambios realizados en su base de datos. Este libro de contabilidad ayuda a Liquibase a auditar su base de
datos y ejecutar cualquier cambio que aún no se haya aplicado. Puede almacenar y versionar su registro de cambios en
cualquier herramienta de control de fuente.

Una **unidad de cambio individual** en su `changeLog` se llama `changeSet`. Cuando desee modificar su base
de datos, simplemente agregue un nuevo `changeSet` y especifique su operación como un `change Type`. Por ejemplo,
puede agregar un `changeSet` para crear una nueva tabla y otro `changeSet` para eliminar una clave
principal.

## [ChangeSet](https://docs.liquibase.com/concepts/changelogs/changeset.html)

Son cambios atómicos que se aplicarían a la base de datos. Cada `changeSet` se identifica de forma única
mediante `id` y `autor`. Cada `changeSet` se ejecuta como una `única transacción`.

Un `changeSet` es la unidad básica de cambio en Liquibase. Almacena todos sus conjuntos de cambios en su
registro de cambios. Sus `changeSet` contienen `changeTypes` que especifican qué hace cada cambio, como
crear una nueva tabla o agregar una columna a una tabla existente.

Un `changeSet` está etiquetado de forma única por los atributos de `autor` e `id` (autor:id), así como
por la ruta del archivo de registro de cambios. La etiqueta `id` es solo un identificador: **no dirige el
orden en que se ejecutan los cambios y no tiene que ser un número entero.** Para implementar el conjunto de cambios con
el comando de `update`, debe incluir tanto el `autor` como el `id`. Puede especificar `Preconditions`,
`Contexts`, `Labels` y otros atributos en conjuntos de cambios individuales para controlar exactamente cuándo se
ejecutan.

**Es una buena práctica especificar solo un `changeType` por `changeSet`.** Al hacerlo, se evitan sentencias de
confirmación automática fallidas que pueden dejar la base de datos en un estado inesperado. Cuando implementas tus
cambios, cada `changeSet` tiene éxito o falla; si falla, puede corregirlo y volver a implementarlo. También
puede agregar comentarios a conjuntos de cambios individuales para explicar por qué son importantes.

## [Change Types](https://docs.liquibase.com/change-types/home.html)

Un `Change Type` es un cambio con formato `XML`, `YAML` o `JSON` independiente de la base de datos que puede especificar
para actualizar su base de datos con Liquibase. Los `Change Types` corresponden a declaraciones `SQL` aplicadas a su
base de datos, como `CREATE TABLE`. Usted especifica el `Change Type` que desea utilizar dentro de un `ChangeSet`
en su `ChangeLog`.

> Se recomienda incluir solo un `Change Type` por `ChangeSet`. Al hacerlo, se evitan
> sentencias de confirmación automática fallidas que pueden dejar la base de datos en un estado inesperado.

## Organización de ChangeLog

Liquibase utiliza archivos de registro de cambios `SQL`, `XML`, `JSON` y `YAML` para enumerar los cambios de la base de
datos en orden secuencial. Los cambios de la base de datos tienen el formato de `changeSets`. Los `changeSets`
contienen `changeType`, que **son tipos de operaciones que se aplican a la base de datos, como agregar una columna o
una clave principal.** Las etiquetas de registro de cambios de contexto, etiquetas y condiciones previas ayudan a
controlar con precisión cuándo se realiza un cambio en la base de datos y en qué entorno de base de datos se implementa.

![organizacion-changelog.png](./assets/01.organizacion-changelog.png)

## Database Changelog y Database Changelog Lock

Cuando implementa sus cambios, Liquibase crea dos tablas en su base de datos: `DATABASECHANGELOG`
y `DATABASECHANGELOGLOCK`.

`DATABASECHANGELOG` realiza un seguimiento de los cambios implementados para que tenga un registro. Liquibase compara
los `changeSets` en el archivo de registro de cambios `(changeLog)` con la tabla de seguimiento `DATABASECHANGELOG` e
implementa solo los `changeSets` nuevos.

`DATABASECHANGELOGLOCK` evita que varias instancias de Liquibase actualicen la base de datos al mismo tiempo. La tabla
administra el acceso a la tabla `DATABASECHANGELOG` durante la implementación y garantiza que solo una instancia de
Liquibase actualice la base de datos.

---

# [Usando Liquibase con Spring Boot](https://contribute.liquibase.com/extensions-integrations/directory/integration-docs/springboot/springboot/)

---

## Dependencias

````xml
<!--Spring Boot 3.2.4-->
<!--Java 21-->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
    </dependency>

    <dependency>
        <groupId>com.microsoft.sqlserver</groupId>
        <artifactId>mssql-jdbc</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
````

Al crear el proyecto desde `Spring Initializr` y agregar la dependencia de `Liquibase`, se nos crea en automático el
directorio `src/main/resources/db/changelog`.

## Application.yml

````yml
server:
  port: 8080
  error:
    include-message: always

spring:
  application:
    name: spring-boot-liquibase

  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=db_liquibase;encrypt=true;trustServerCertificate=true;
    username: sa
    password: magadiflo

  jpa:
    properties:
      hibernate:
        format_sql: true

  liquibase:
    change-log: classpath:db/changelog-master.yml

logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.orm.jdbc.bind: TRACE
````

**NOTA**

- Por defecto, se espera que el archivo changeLog de Liquibase esté en `db/changelog/changelog-master.yml`; pero estamos
  cambiando el valor de la configuración `spring.liquibase.change-log` para ponerlo en
  `src/main/resources/db/changelog-master.yml`.

## Configurando el changelog-master.yml

Recordemos que Liquibase nos permite trabajar con distintos tipos de archivos SQL, XML, JSON y `YML`. En este proyecto
trabajaremos con `YML`, es por eso que creamos el archivo `changelog-master.yml`.

Agregamos el siguiente contenido a dicho archivo:

````yaml
databaseChangeLog:
````

La configuración `databaseChangeLog` es el punto de partida a partir del cual se agregan los `changeSets`,
pero por el momento, como queremos ejecutar la aplicación, solo dejaremos dicha configuración, ya que el
archivo  `changelog-master.yml` no puede estar vacío, sino nos va a mostrar un error.

## Ejecutando aplicación

Si hasta este punto ejecutamos la aplicación, veremos que todo se ejecutará exitosamente y si revisamos la base de datos
veremos que se nos han creado dos tablas:

![02.run-first.png](./assets/02.run-first.png)
