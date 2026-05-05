# Sistema Taller - Task Manager (Grupo 4)

Este es un proyecto desarrollado en Java para la gestión de tareas (Task Management). Es parte de la arquitectura de microservicios de SmartBank (Microservicio 1).

## 🛠️ Tecnologías Utilizadas

* **Java**: Versión 17
* **Maven**: Gestor de dependencias y construcción del proyecto
* **JUnit 5**: Framework de testing para el aseguramiento de la calidad del código

## 📂 Estructura del Proyecto

* `src/main/java/com/grupo4/`: Contiene el código fuente principal de la aplicación (`Main.java`, `Task.java`, `TaskManager.java`).
* `src/test/java/com/grupo4/`: Contiene las pruebas unitarias del proyecto (`TaskManagerTest.java`).
* `pom.xml`: Archivo de configuración de Maven.

## 🚀 Cómo empezar

### Prerrequisitos

* Tener instalado **Java Development Kit (JDK) 17** o superior.
* Tener instalado **Apache Maven**.

### Compilación y Construcción

Para compilar el proyecto y descargar todas las dependencias, ejecuta el siguiente comando en la raíz del proyecto (donde se encuentra el `pom.xml`):

```bash
mvn clean install
```

### Ejecutar la Aplicación

Puedes ejecutar la aplicación desde tu IDE favorito ejecutando la clase `com.grupo4.Main`, o puedes usar Maven:

```bash
mvn exec:java -Dexec.mainClass="com.grupo4.Main"
```

*Nota: Asegúrate de tener el plugin `exec-maven-plugin` configurado o usar comandos estándar de Java una vez compilado.*

### Ejecutar las Pruebas (Test)

El proyecto incluye pruebas unitarias con JUnit 5. Para ejecutarlas, utiliza el siguiente comando de Maven:

```bash
mvn test
```

Los reportes de las pruebas se generarán en la carpeta `target/surefire-reports/`.

## 👥 Autores

* **Grupo 4**
