# Modulo de Expansión de Red (MER)
---

## Tecnologías usadas en el proyecto

- Java Standard Edition versión 1.8
- Java Enterprise Edition versión 7
- Java Server Faces
- PrimeFaces version 8.0
- Maven
- Oracle SQL
- PLSQL
- JPA
- CSS
- JQuery
- JavaScript
- HTML

---

## Guia de inicio

### Requisitos de instalación.

- Java JDK versión 1.8
- Maven
- Git
- Servidor Weblogic versión 12.1.2.3
- Entorno de Desarrollo (IDE)
    - [Apache Netbeans](https://netbeans.apache.org/download/index.html)
    - [Intellij Idea Ultimate](https://www.jetbrains.com/idea/download/?var=1&section=windows)) (Requiere pago e
      licencia)
    - o el IDE de preferencia que tenga soporte para el servidor WebLogic.

### Instalación

1. Abrir la terminal de git (git Bash) y clonar el repositorio con el comando.
   `git clone https://OrgClaroColombia@dev.azure.com/OrgClaroColombia/MER/_git/MER-Front`

> Para poder bajar las fuentes del proyecto se debe tener acceso a la plataforma Azure
> y tener los permisos de acceso
> al [repositorio de MER](https://dev.azure.com/OrgClaroColombia/MER/_git/MER-Front/branches?_a=all) en ella.

2. Compilar el proyecto ejecutando el archivo compile.sh ubicado en el directorio
   principal del proyecto, a través de la terminal de git (git bash) usando el comando
   `sh compile.sh`

### Despliegue

Para desplegar la aplicación en ambiente local, se debe inicializar el servidor WebLogic
y configurar los origgenes dedatos (DataSources) requeridos (jdbc/Dir y jdbc/GestionSeguridad)

### Uso

Para acceder a la aplicación se puede realizar con cualquiera de las siguientes opciones:

1. Acceder a través de módulo de gestión y pasar hacia MER.
2. Acceder directamente a través del login de la aplicación
   [login MER](http://localhost:7001/catastro-warIns/view/MGL/template/login.xhtml?faces-redirect=true)

---

## Arquitectura del código

Dentro de los módulos principales de la aplicación, se tiene una estructura definida
por 3 módulos internos, el **ear**, el **ejb** y el **war**.

A continuación se explica la estructura de carpetas y módulos más importantes y su ubicación:

- [CMatricesAs400Services](CMatricesAs400Services) Representa la exposición de la capa de
  servicios de MER.

  En este directorio se encuentran los módulos
    * [CMatricesAs400Services-ear](CMatricesAs400Services/CMatricesAs400Services-ear)
    * [CMatricesAs400Services-ejb](CMatricesAs400Services/CMatricesAs400Services-ejb)
    * [CMatricesAs400Services-war](CMatricesAs400Services/CMatricesAs400Services-war)


- [catastro](MGL/catastro) Representa la capa de presentación (Front) de la aplicación MER

  En este directorio se encuentran los módulos
    * [catastro-ear](MGL/catastro/catastro-ear)
    * [catastro-ejb](MGL/catastro/catastro-ejb)
    * [catastro-war](MGL/catastro/catastro-war)

### Configuración

En estos archivos se encuentran las configuraciones más importantes del proyecto:

- `pom.xml`: Archivo de configuración de Maven que contiene las dependencias del proyecto.
- `web.xml`: Archivo de configuración de la aplicación web en Java EE.
- `faces-config.xml`: Archivo de configuración de JSF que define las configuraciones específicas del framework.
- `log4j2.properties`: Archivo de propiedades de LOG4J2, para determinar la escritura de logs de la aplicación.

---

## Contribución

> Para poder contribuir al proyecto se debe tener acceso a la plataforma Azure
> y tener los permisos de acceso
> al [repositorio de MER](https://dev.azure.com/OrgClaroColombia/MER/_git/MER-Front/branches?_a=all) en ella.

1. Realiza una solicitud (RQ / WO) de creación de rama sobre el proyecto
   a través de la plataforma [MyIt](https://myit.claro.com.co:8443/dwp/app/#/account/login)
   (usando el usuario de red de claro para iniciar sesión) en la opción:

   Servicios de IT para IT → Asesorías y Solicitudes → Plataformas y/o Aplicaciones →
   Asesorías y Solicitudes - Plataformas y/o Aplicaciones → Control Estratégico::ClCD →
   Creación ramas y/o pipeline.

2. Realiza los cambios y realiza un commit (`git commit -m "Mensaje descriptivo del commit"`).

3. Envía tus cambios al repositorio remoto (`git push`).

4. Una vez completado el ajuste o desarrollo de contribución al código, debe realizarse
   una petición de validación de estándares a través de la
   plataforma [MyIt](https://myit.claro.com.co:8443/dwp/app/#/account/login)
5. Se debe crear un pull request desde nueva rama de trabajo hacia la rama Develop
   en el repositorio de [Azure](https://dev.azure.com/OrgClaroColombia/MER/_git/MER-Front).

## Autores del proyecto

- Inspira Claro
- Claro Colombia
