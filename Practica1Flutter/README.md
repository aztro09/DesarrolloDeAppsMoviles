# Detalles del desarrollo en flutter

## Funcionamiento
https://github.com/user-attachments/assets/5ea718fb-2ccd-4dcd-91ea-c1cea6947807

## Desarrollo

Para el desarrollo de esta practica se tomaron en cuenta los 5 fragments/widgets solicitados, ademas del screen principal, se agrego una segunda screen en la cual se contendria la informacion y la navegacion entre los diferentes fragments que mostraban la informacion.

La app se organizo de manera que el archivo  `home_screen` fuera el de inicio de la app llamandolo desde el main.dart, en este home si puso el titulo de la app y ademas un boton al cual se le daria click para pasar a la segunda screen (`second_screen`).

Ahora ya que estemos dentro del second screen se diseño de manera que se tuviero el titulo de la app principal y un boton para regresar al home en caso de necesitarlo. Debajo estaria el navegador el cual funcionaria como una lista deslizable en la cual si se daba click al titulo de cada widget seriamos dirigidos al widget donde se puso la informacion del mismo ademas de botones interactivos para representar el funcionamiento de estos.

### Dificultades
La dificultad prinpical que en el transcurso del desarrollo de la app fue la definicion de los widgets, pues tenia que ubicar y organizar los contenidos para tener buena navegacion, ademas que dentro del codigo se debia definir como Statless o Statful Widget, al final se pudo solucionar revisando lo visto en clase sobre los widgets y ademas la docu de Android Studio para Flutter.

Otra dificultad fue la integracion especifica del `info_widget`, pues el codigo de este y las definiciones estaban correctos y en `second_screen`, que es donde estaban importados y se hacia el call de la funcion de cada widget para poder acceder a ellos, no conseguia llamar de manera correcta al widget de informacion cuando se tenia el importe de este correcto tambien, apareciendo el error `The method 'InfoWidget' isn't defined for the type 'SecondScreen'`. Lo que hice fue abrir y cerrar tanto el IDE de Android Studio como el folder donde se ubicaba el proyecto, y abrir nuevamente todo, ademas en la terminal corri el comando `dart fix --dry-run` para que sugiriera que cambios de podian hacer, luego se ejecuto el comando `dart fix --apply` para implementar los cambios sugeridos. Una vez terminado todo lo anterior el codigo dejo de marcar el error de importe y se pudo incializar la app de manera correcta y probar sus funciones las cuales tambien funcionaban de manera correcta.
