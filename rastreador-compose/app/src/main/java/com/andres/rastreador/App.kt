
package com.andres.rastreador

import android.app.Application
import androidx.preference.PreferenceManager
import org.osmdroid.config.Configuration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicializa osmdroid y define un user-agent válido
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        Configuration.getInstance().load(this, sharedPrefs)
        Configuration.getInstance().userAgentValue = packageName
    }
}
