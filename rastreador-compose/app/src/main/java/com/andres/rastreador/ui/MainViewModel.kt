
package com.andres.rastreador.ui

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.andres.rastreador.data.AppDatabase
import com.andres.rastreador.data.LocationEntity
import com.andres.rastreador.data.LocationRepository
import com.andres.rastreador.prefs.PreferencesRepository
import com.andres.rastreador.service.LocationTrackingService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = LocationRepository(AppDatabase.get(app).locationDao())
    private val prefs = PreferencesRepository(app)

    val locations: StateFlow<List<LocationEntity>> =
        repo.getAllFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val intervalMs = prefs.intervalMsFlow.stateIn(viewModelScope, SharingStarted.Eagerly, 10_000L)
    val theme = prefs.themeFlow.stateIn(viewModelScope, SharingStarted.Eagerly, "GUINDA")
    val notifDiscreta = prefs.notifDiscretaFlow.stateIn(viewModelScope, SharingStarted.Eagerly, true)
    val trackingActive = prefs.trackingActiveFlow.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun setInterval(ms: Long) = viewModelScope.launch { prefs.setInterval(ms) }
    fun setTheme(t: String) = viewModelScope.launch { prefs.setTheme(t) }
    fun setNotifDiscreta(value: Boolean) = viewModelScope.launch { prefs.setNotifDiscreta(value) }

    fun startTracking() {
        val ctx = getApplication<Application>()
        ctx.startForegroundService(Intent(ctx, LocationTrackingService::class.java))
    }
    fun stopTracking() {
        val ctx = getApplication<Application>()
        ctx.stopService(Intent(ctx, LocationTrackingService::class.java))
    }
}
