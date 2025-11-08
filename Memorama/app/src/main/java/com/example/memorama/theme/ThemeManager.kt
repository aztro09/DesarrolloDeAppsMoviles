package com.example.memorama.theme

import android.content.Context
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import java.util.prefs.Preferences

object ThemeManager{
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val TEMA_KEY = stringPreferencesKey("tema_seleccionado")

    suspend fun guardarTema(context: Context, tema: String){
        context.dataStore.edit {prefs ->
            prefs[TEMA_KEY] = tema
        }
    }

    fun obtenerTema(context: Context): Flow<String>{
        return context.dataStore.data.map { prefs ->
            prefs[TEMA_KEY] ?: "guinda"
        }
    }
}