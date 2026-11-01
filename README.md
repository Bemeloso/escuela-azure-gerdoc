# ESCUELA: Tomcat, Hibernate y Spring Boot en Azure

Aplicación de la práctica de Programación Web de Gerdoc. Incluye consulta, alta, cambio y baja de ALUMNO, MATERIA, PROFESOR, ALUMNO_MATERIA y PROFESOR_MATERIA sobre una misma base ESCUELA.

## Proyectos

- `Web`: aplicación Maven WAR para Tomcat 9, Java 17, JSP, Bootstrap, Servlets, servicios y DAO con Hibernate 5.6. Cada operación abre su sesión y sus cambios utilizan transacciones.
- `Escuela-SpringBoot`: proyecto descargado de Spring Initializr con Java 17, Spring Web, Spring Data JPA, Validation, MySQL y Lombok. Contiene entidades, repositorios, servicios, controladores y Swagger para los cinco catálogos.
- `sql/ESCUELA.sql`: tablas, llaves foráneas, clave compuesta de profesor/materia, restricción de inscripción única y datos de demostración.
- `deploy`: instalación en Ubuntu 22.04, servicio Spring Boot y proxy Apache.
- `scripts`: comprobaciones de CRUD, integridad y persistencia por HTTP.

La estructura toma como referencia los proyectos de clase [Escuela-SpringBoot](https://github.com/gerdoc-ipn/Escuela-SpringBoot) y Web-1, rama Hibernate2. El ZIP original de Initializr se conserva en la entrega local. Se normalizó la versión generada `4.1.2.RELEASE` al identificador Maven publicado `4.1.2`.

## Abrir en IntelliJ IDEA

Abre esta carpeta y carga el `pom.xml` principal como proyecto Maven. También puedes abrir cada subproyecto por separado. Selecciona JDK 17 o superior con compilación de destino Java 17. Habilita el procesamiento de anotaciones para Lombok si IntelliJ lo solicita.

## Ejecutar con MySQL

1. Crea una base nueva con `sql/ESCUELA.sql`; el archivo no elimina una base existente.
2. Crea un usuario de aplicación con SELECT, INSERT, UPDATE y DELETE sobre ESCUELA. Configura las variables mostradas en `.env.example` dentro de la ejecución de IntelliJ o del servicio. Los proyectos leen variables de entorno; no cargan automáticamente el archivo `.env`.
3. Compila los dos proyectos:

```sh
mvn -DskipTests clean package
```

4. Copia `Web/target/Web.war` en `webapps` de Tomcat 9 y asigna a Tomcat las mismas variables de base de datos. Arranca Tomcat con Java 17.
5. Inicia Spring Boot:

```sh
java -jar Escuela-SpringBoot/target/escuela-api.jar
```

Spring Boot escucha en `127.0.0.1:9080`. Tomcat utiliza `8080`. En Azure, Apache publica `/Web/` hacia Tomcat y las demás rutas hacia Spring Boot. La página principal web contiene enlaces a los cinco catálogos.

## Servicios REST

Cada conjunto ofrece GET de lista, GET individual, POST, PUT y DELETE. Swagger está en `/swagger-ui.html` y la definición OpenAPI en `/v3/api-docs`.

| Catálogo | Ruta | Clave individual |
|---|---|---|
| Alumno | `/api/alumnos` | `/{id}` |
| Materia | `/api/materias` | `/{id}` |
| Profesor | `/api/profesores` | `/{id}` |
| Alumno/materia | `/api/alumno-materia` | `/{id}` |
| Profesor/materia | `/api/profesor-materia` | `/{profesorId}-{materiaId}` |

Ejemplos de cuerpos JSON:

```json
{"nombre":"Alumno de demostración","edad":20,"promedio":9.25}
```

```json
{"alumnoId":1,"materiaId":2,"calificacion":9.5}
```

```json
{"profesorId":1,"materiaId":2}
```

Profesor/materia conserva la clave primaria compuesta de la base original. Cuando se cambian sus dos referencias, se reemplaza la asignación dentro de una única transacción. Las llaves foráneas impiden borrar alumnos, materias o profesores con relaciones vigentes; primero deben eliminarse esas relaciones. No se borran relaciones en cascada.

## Desplegar en Ubuntu 22.04

Transfiere la carpeta con ambos proyectos ya compilados a la máquina y ejecuta:

```sh
sudo bash deploy/instalar-ubuntu22.sh
```

El instalador utiliza paquetes oficiales de Ubuntu para Apache, MySQL 8, Tomcat 9 y Java 17. Conserva la administración local de MySQL por socket, genera una contraseña para un usuario de aplicación con permisos limitados y la guarda únicamente en `/etc/escuela.env`, con permisos restringidos. No utiliza las credenciales de ejemplo del profesor ni incluye contraseñas en el código.

No abras MySQL, Tomcat o Spring Boot directamente a Internet. Los servicios de aplicación escuchan en loopback y Apache atiende el sitio web por el puerto 80. El grupo de seguridad de Azure debe admitir el acceso SSH acordado y HTTP.

Comandos para la demostración:

```sh
sudo systemctl start mysql apache2 tomcat9 escuela-api
sudo systemctl is-active mysql apache2 tomcat9 escuela-api
java -version
mysql --version
curl http://127.0.0.1:9080/api/health
python3 scripts/probar-rest.py http://127.0.0.1:9080/api
python3 scripts/probar-web.py http://127.0.0.1/Web http://127.0.0.1:9080/api
sudo mysql ESCUELA -e 'SHOW TABLES; SELECT * FROM ALUMNO;'
```

Detén y desasigna la VM desde Azure al acabar de trabajar o grabar. El disco y la IP pública pueden seguir teniendo costo mientras existan.

## Verificación local

El perfil Maven `local-test` añade H2 exclusivamente para comprobar los proyectos en una base de pruebas aislada. Las pruebas locales con H2 no sustituyen las pruebas finales con MySQL en Azure. Compila sin ese perfil los artefactos de despliegue. Los informes deben identificar expresamente dónde y con qué motor se ejecutaron las pruebas.

Las claves SSH, los archivos de credenciales y los artefactos `target/` están excluidos del repositorio.

## Validación realizada en Azure: 1 de noviembre de 2026

Se desplegaron ambos proyectos en Ubuntu Server 22.04.5 LTS, B2as v2 (2 vCPU y 8 GiB). MySQL 8.0.46, Java 17, Apache y Tomcat 9 se instalaron desde Ubuntu; Spring Boot corre como servicio.

Pasaron 52 comprobaciones REST y 53 de formularios sobre MySQL ESCUELA. También se comprobó por SQL, REST y JSP la persistencia de un registro después de reiniciar Tomcat y Spring Boot. Los datos creados para las pruebas se limpiaron al terminar. Los resultados están en [evidencias/azure](evidencias/azure/).

La aplicación está preparada para acceder, con la máquina encendida, a `http://158.23.58.178/Web/` y `http://158.23.58.178/swagger-ui.html`. La VM debe desasignarse cuando no se use. Estas evidencias no sustituyen el video de la actividad: la grabación sigue pendiente.
