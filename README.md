# Taller 6 - AREP
En este taller se implementó una arquitectura que debía ser desplegada en *AWS* usando *EC2* y *Docker*. La aplicación permite hacer el registro y la consulta de las 
10 últimas cadenas almacenadas sobre una base de datos de *Mongo*. Ahora, para manejar la carga de forma eficiente, se tiene una fachada que realiza el balanceo de cargas de *Round Robin*, 
para distribuir las solicitudes entre las tres instancias del servicio de *LogService*.

## Empezando
Las siguientes instrucciones permiten que obtenga una copia del proyecto en funcionamiento.

### Prerrequisitos
1. Maven y JDK para compilar y ejecutar el proyecto.
2. Docker para poder descargar las imágenes y ejecutarlas.
3. Verificar disponibilidad de puertos.
4. Tener conexión a internet.

### Instalación
1. Por terminal
   
Para obtener el proyecto y ejecutarlo, debe ser descargado en formato zip o puede ser clonado desde el repositorio de GitHub. Con el proyecto en su máquina, debe acceder al
directorio que contiene el proyecto. Luego, debe descargar las dependencias del proyecto, para esto ejecute el comando `mvn clean install`. Ahora se deben constuir las imágenes, para
esto se usan los comandos `docker build -t dockerservice -f Dockerfile.logservice .` y `docker build -t dockerfacade -f Dockerfile.logfacade .`. Después se ejecuta el siguiente comando
`docker-compose up -d`. Por último, para verificar que se crearon los servicios se ejecuta `docker ps`.

5. Con Docker

Para descargar las imágenes se ejecuta `docker pull jessicadmunozo/taller6arep:facade` y `docker pull jessicadmunozo/taller6arep:service`. Ahora se etiquetan las imágenes para que se pueda
ejecutar correctamente, entonces se usan los comandos `docker tag jessicadmunozo/taller6arep:facade dockerfacade` y `docker tag jessicadmunozo/taller6arep:service dockerservice`. Debe crear el archivo *docker-compose.yml*, para esto puede usar un editor como notepad y colocarle la configuración que se encuentra en este repositorio para
dicho archivo. Por último se ejecuta el comando `docker-compose up -d`.

## Despliegue
Con el proyecto corriendo debe abrir en un navegador la siguiente dirección: http://localhost:8088/Response.html.

## Diseño
Se construyeron dos contenedores *Docker*. El primero contiene todo lo relacionado con la fachada; el cliente y su formulario, la conexión a las instancias del *LogService* y 
el balanceador de cargas. 

De modo que, en la clase fachada de *LogServerFacade* se define el servidor, que contiene un arreglo con las URLs de los servicios de *LogService* que son quienes atenderán las solicitudes. Para realizar la conexión a dichas instancias se usa la clase *RemoteLogServiceInvoker* que es la encargada también de distribuir las solicitudes entre las URLs por medio del balanceador de cargas de *Round Robin*. Dentro de este contenedor también se encuentra el formulario del cliente en el cuál puede ingresar la cadena que posteriormente será almacenada en la base de datos.

El otro contenedor tiene todo lo relacionado con el servicio de registro y consulta *LogService*. De modo que, se realiza la conexión a la base de datos por medio de la clase *MongoUtil*, se define la ruta y se registra la cadena ingresada en el formulario a la base de datos con la fecha. Además, se devuelve un *JSON* que contiene los últimos 10 registros que fueron almacenados. Para esto se usa la clase *Log* que tiene los métodos para añdir y recuperar registros de la base de datos.

Gracias al archivo *docker-compose* se tienen los 5 servicios necesarios. Las 3 instancias del contenedor del servicio, la fachada y la base de datos de Mongo. Lo cual fue desplegado en AWS con EC2.

![image](https://github.com/JessicaDMunozO/Taller6-AREP/assets/123814482/3c934966-af71-472e-b247-3d4544589316)

## Evaluación
Con la dirección http://localhost:8088/Response.html puede ver el formulario para ingresar una cadena.

![image](https://github.com/JessicaDMunozO/Taller6-AREP/assets/123814482/c44751fe-ab09-4071-8ca7-2b2800a8cd45)

Si ingresa una cadena y le da clic en el botón de *Submit* se muestran la cadena almacenada con la fecha.

![image](https://github.com/JessicaDMunozO/Taller6-AREP/assets/123814482/1898ef2f-72ce-4c17-910d-17653339829e)

Con 10 cadenas se ve del siguiente modo:

![image](https://github.com/JessicaDMunozO/Taller6-AREP/assets/123814482/afdb64cb-28a9-4efd-9aa6-c6167d4dfa31)

Y al ingresar una cadena más, sólo va a mostrar las últimas 10 cadenas registradas, por ende ya no mostraría el registro inicial con la cadena "hola"

![image](https://github.com/JessicaDMunozO/Taller6-AREP/assets/123814482/42f36b52-3eda-4879-8e54-54f8487dca67)

## Despliegue en AWS
https://youtu.be/iCIAkcuV7bs

## Construido con
Maven - Gestión de dependencias

Docker - Creación y ejecución de contenedores

AWS - Despliegue

## Versiones
Git - Control de versiones

## Autor
Jessica Daniela Muñoz Ossa
