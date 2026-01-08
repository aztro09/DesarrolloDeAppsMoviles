
package com.andres.rastreador.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

object Keys {
    val INTERVAL_MS = longPreferencesKey("interval_ms")
    val THEME = stringPreferencesKey("theme")
    val NOTIF_DISCRETA = booleanPreferencesKey("notif_discreta")
    val TRACKING_ACTIVE = booleanPreferencesKey("tracking_active")
}

class PreferencesRepository(private val context: Context) {
    val intervalMsFlow = context.dataStore.data.map { it[Keys.INTERVAL_MS] ?: 10_000L }
    val themeFlow = context.dataStore.data.map { it[Keys.THEME] ?: "GUINDA" }
    val notifDiscretaFlow = context.dataStore.data.map { it[Keys.NOTIF_DISCRETA] ?: true }
    val trackingActiveFlow = context.dataStore.data.map { it[Keys.TRACKING_ACTIVE] ?: false }

    suspend fun setInterval(ms: Long) { context.dataStore.edit { it[Keys.INTERVAL_MS] = ms } }
    suspend fun setTheme(theme: String) { context.dataStore.edit { it[Keys.THEME] = theme } }
    suspend fun setNotifDiscreta(value: Boolean) { context.dataStore.edit { it[Keys.NOTIF_DISCRETA] = value } }
    suspend fun setTrackingActive(value: Boolean) { context.dataStore.edit { it[Keys.TRACKING_ACTIVE] = value } }
}
