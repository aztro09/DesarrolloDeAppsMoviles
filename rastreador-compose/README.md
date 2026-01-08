
# Rastreador (Compose + OSMDroid/OpenStreetMap + Room + Firestore)

Este proyecto usa **OpenStreetMap** vía **OSMDroid** para la visualización del mapa y la ruta.

## Setup rápido
1. **Permisos**: la app solicita ubicación y notificaciones; incluye `INTERNET` y `ACCESS_NETWORK_STATE` para descargar tiles. 
2. **OSMDroid**: se inicializa en `App.kt` con `Configuration.getInstance().load(...)` y `userAgentValue`.
3. **Firestore**: agrega `google-services.json` en `app/` y habilita Cloud Firestore.

## Qué hace
- Servicio foreground de rastreo con intervalos (10s, 60s, 5min).
- Historial local (Room) + subida en tiempo real a Firestore.
- Mapa **OSMDroid** con marcador actual y polyline.
- Temas Guinda/Azul.

## Nota
- Si necesitas **offline tiles** permanentes, revisa el wiki de OSMDroid sobre archivos SQLite/ZIP/MBTiles.
