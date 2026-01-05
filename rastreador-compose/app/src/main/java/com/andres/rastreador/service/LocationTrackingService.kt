
package com.andres.rastreador.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.andres.rastreador.data.AppDatabase
import com.andres.rastreador.data.LocationRepository
import com.andres.rastreador.prefs.PreferencesRepository
import com.andres.rastreador.cloud.FirestoreSync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class LocationTrackingService : Service() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val fused by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val repo by lazy { LocationRepository(AppDatabase.get(this).locationDao()) }
    private val prefs by lazy { PreferencesRepository(this) }
    private val cloud by lazy { FirestoreSync(this) }

    private var locationCallback: LocationCallback? = null
    private var latestContent: String = "Inicializando…"

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.ensureChannel(this)
        scope.launch {
            prefs.notifDiscretaFlow.collect { discreta ->
                updateForegroundNotification(discreta)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            val intervalMs = try { prefs.intervalMsFlow.first() } catch (_: Throwable) { 10_000L }
            startTracking(intervalMs)
            prefs.setTrackingActive(true)
        }
        return START_STICKY
    }

    private suspend fun startTracking(intervalMs: Long) {
        if (!hasLocationPermission()) {
            Log.w("Service", "Permisos de ubicación no concedidos")
            stopSelf()
            return
        }

        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, intervalMs)
            .setMinUpdateIntervalMillis(intervalMs)
            .setWaitForAccurateLocation(true)
            .build()

        locationCallback?.let { fused.removeLocationUpdates(it) }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val loc = result.lastLocation ?: return
                val lat = loc.latitude
                val lon = loc.longitude
                val acc = loc.accuracy
                val ts = System.currentTimeMillis()
                latestContent = "(${"%.6f".format(lat)}, ${"%.6f".format(lon)}) • ±${acc.roundToInt()} m"
                scope.launch {
                    repo.insert(lat, lon, acc, ts)
                    // Subir a Firestore en tiempo real
                    try { cloud.uploadLocation(com.andres.rastreador.data.LocationEntity(latitude = lat, longitude = lon, accuracy = acc, timestamp = ts)) } catch (_: Throwable) {}
                }
                scope.launch {
                    val discreta = try { prefs.notifDiscretaFlow.first() } catch (_: Throwable) { true }
                    updateForegroundNotification(discreta)
                }
            }
        }

        val discreta = try { prefs.notifDiscretaFlow.first() } catch (_: Throwable) { true }
        startForeground(NOTIF_ID, NotificationHelper.buildNotification(this, discreta, latestContent))
        fused.requestLocationUpdates(request, locationCallback!!, mainLooper)
    }

    private suspend fun updateForegroundNotification(discreta: Boolean) {
        val notif = NotificationHelper.buildNotification(this, discreta, latestContent)
        val mgr = getSystemService(android.app.NotificationManager::class.java)
        mgr.notify(NOTIF_ID, notif)
    }

    private fun hasLocationPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        return fine || coarse
    }

    override fun onDestroy() {
        locationCallback?.let { fused.removeLocationUpdates(it) }
        scope.launch { prefs.setTrackingActive(false) }
        scope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = null
}
