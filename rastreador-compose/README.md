
# Proyecto base: Rastreador (Jetpack Compose + Room + Firebase Firestore + Google Maps)

Este proyecto cumple:
- Rastreo de ubicación en **Foreground Service** con intervalos configurables (10s, 60s, 5min).
- Mapa de **Google Maps (Compose)** con marcador y **ruta (Polyline)**.
- Historial local en **Room** (lat, lon, timestamp, precisión).
- Envío en tiempo real a **Firebase Firestore** (colección `locations`).
- UI en **Jetpack Compose**: selector de intervalo, iniciar/detener, historial, precisión, indicador de estado.
- Temas **Guinda (IPN)** y **Azul (ESCOM)** con soporte claro/oscuro.
- Notificación persistente configurable en modo **discreto**.

## Pasos de configuración

1. **Clave de Google Maps**:
   - Habilita *Maps SDK for Android* en Google Cloud.
   - Coloca tu API Key en `app/src/main/res/values/strings.xml` en `google_maps_key`.

2. **Firebase Firestore**:
   - Crea un proyecto en Firebase Console y agrega una app **Android** con el `applicationId` `com.andres.rastreador`.
   - Descarga el archivo `google-services.json` y colócalo en `app/`.
   - Asegúrate de habilitar **Cloud Firestore** y reglas adecuadas para desarrollo.

3. **Abrir en Android Studio**:
   - Puedes crear un proyecto vacío y reemplazar su módulo `app/` por este `app/`, o abrir esta carpeta directamente.
   - Sin *gradle wrapper* incluido, Android Studio usará el **Gradle embebido** o te permitirá generar el wrapper automáticamente.
   - Sincroniza Gradle y ejecuta en un dispositivo/emulador con GPS.

4. **Permisos**:
   - Al abrir la app se solicitarán permisos de ubicación y notificaciones (Android 13+).
   - Para actualizaciones en background prolongadas, también se solicita `ACCESS_BACKGROUND_LOCATION` (Android 10+).

5. **Firestore colección**:
   - Los documentos se escriben en `locations` con campos: `latitude`, `longitude`, `accuracy`, `timestamp`, `deviceId`.

## Notas
- La notificación de un *Foreground Service* no puede ocultarse completamente si el rastreo está activo. El modo **discreto** reduce su prioridad y contenido.
- Si cambias el intervalo mientras el servicio está corriendo, simplemente detén y vuelve a iniciar el rastreo.

¡Listo! Compila y pruébalo.
