# Detalles de la Practica 1

## Vistas de la App
<img width="383" height="824" alt="image" src="https://github.com/user-attachments/assets/ef3a212b-dbd2-4401-96f0-9cb2af29f7b6" />
<img width="382" height="831" alt="image" src="https://github.com/user-attachments/assets/b9ef3972-8726-4b6d-918b-ffc2c704b5aa" /> <img width="382" height="836" alt="image" src="https://github.com/user-attachments/assets/f101907e-c67d-4f70-867e-76dd84860cc1" />
<img width="390" height="834" alt="image" src="https://github.com/user-attachments/assets/bb65c3dc-b769-473f-bea0-7fa003a96e5f" />
<img width="384" height="842" alt="image" src="https://github.com/user-attachments/assets/bb6e6eee-43cb-4a9b-86cf-839f97aedf20" />
<img width="381" height="847" alt="image" src="https://github.com/user-attachments/assets/b3de9ea0-c172-4910-94ea-13a72a095c3a" />






## Ejercicio 1
Se instalo Android Studio siguiendo las instrucciones de la pagina oficial:
<img width="930" height="587" alt="image" src="https://github.com/user-attachments/assets/78bd15b2-aa3c-46fb-a158-2ec756cbf5fd" />

Posteriormente se pide la instalacion de varias dependencias, en este caso Node.js, Git, Github y el JDK ya se encontraban instaladas en mi equipo, por el otro lado, Maven, Flutter y Docker no.
### Instalacion de Flutter
Ingresamos a la pagina oficial de [Flutter](https://docs.flutter.dev/) y seleccionamos el boton que dice "Get Started", selccionamos "Windows" y luego "Android". Finalmente seguimos las instrucciones de la pagina:
<img width="1502" height="809" alt="image" src="https://github.com/user-attachments/assets/efdeb8b5-b200-44bf-8e61-14e3f92ad33f" />.

Para el desarrollo de las apps seleccionaremos Android Studio como IDE, como este ya esta descargado se hara la configuracion de este para un buen desarrollo de las apps. Se abrira Android Studio y en la ventana de bienvenida nos iremos a la seccion de "Plugins" y buscaremos "Flutter" y lo instalaremos:
<img width="989" height="254" alt="image" src="https://github.com/user-attachments/assets/11994394-2c32-4d4d-b77d-6efaeffe41f6" />
Despues del reinicio que nos pedira luego de la descarga, deberemos de revisar que el plugin de "Dart" este instalado, en caso de que no se debera de instalar.


### Instalacion de Docker
Para docker ingresaremos al link de las instrucciones para descargar [Docker Desktop](https://docs.docker.com/desktop/setup/install/windows-install/). En mi caso, una vez dentro de la pagina se seleccionara el .exe para la version de Docker x86-64:
<img width="335" height="52" alt="image" src="https://github.com/user-attachments/assets/2daad584-0968-4971-8fea-133f1ef4ca25" />

Despues de la descarga se ejecutara el .exe y se seguiran las instrucciones del instalador hasta tener la ventana principal de Docker Desktop:
<img width="1439" height="781" alt="image" src="https://github.com/user-attachments/assets/5b9f70c3-83f3-4087-b981-357b5bf4201a" />



### Instalacion de Maven
Se ingreso al [link de descarga](https://maven.apache.org/download.cgi) para Apache Maven, y se selecciono el archivo "Binary zip archive	| apache-maven-3.9.11-bin.zip" para descargar.

Una vez descargado, en el explorador de archivos se ubicara el .zip y extraera, elegi como ubicacion de extraccion la carpeta de "Program Files". Despues de extraerse ingresaremos a "apache-maven-3.9.11" -> "bin" y copiaremos la ruta de la carpeta una vez dentro de "bin".

Posterior a copiar la ruta, se oprimira la tecla de Windows y se buscara "Edit enviroment varibles": <img width="400" height="198" alt="image" src="https://github.com/user-attachments/assets/ceece667-be44-449c-87b7-77aea3555660" />. 

Se ingresara, luego se buscara en user variables y system variables la variable "Path": 

<img width="207" height="476" alt="image" src="https://github.com/user-attachments/assets/1c53b571-cf11-4b92-9a84-74a35c82efea" />. 

Le damos doble click y seleccionamos "New" para copiar la ruta de bin en ella y guardar las modificaciones.
Para verificar que se instalo de manera correcta accederemos al CMD y escribiremos `mvn --version`, si se instalo de manera correcta se mostrara lo siguiente en la terminal: 
<img width="805" height="105" alt="image" src="https://github.com/user-attachments/assets/ed0c0ada-4ae1-417d-bb09-54b005a9019e" />
