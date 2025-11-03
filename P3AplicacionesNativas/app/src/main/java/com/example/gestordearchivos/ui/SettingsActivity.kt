package com.example.gestordearchivos.ui

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.gestordearchivos.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var themeSelector: RadioGroup
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        // Aplicar el tema antes de setContentView
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val currentTheme = prefs.getString("app_theme", "guinda")
        if (currentTheme == "guinda") {
            setTheme(R.style.Theme_GestorDeArchivo_Guinda)
        } else {
            setTheme(R.style.Theme_GestorDeArchivo_Azul)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        themeSelector = findViewById(R.id.themeSelector)
        saveButton = findViewById(R.id.saveThemeButton)

        if (currentTheme == "guinda") {
            themeSelector.check(R.id.guindaTheme)
        } else {
            themeSelector.check(R.id.azulTheme)
        }

        saveButton.setOnClickListener {
            val selectedTheme = when (themeSelector.checkedRadioButtonId) {
                R.id.guindaTheme -> "guinda"
                R.id.azulTheme -> "azul"
                else -> "guinda"
            }

            if (selectedTheme != currentTheme) {
                prefs.edit().putString("app_theme", selectedTheme).apply()
                recreate() // Recarga la actividad con el nuevo tema
            }
        }
    }
}