package com.example.gestordearchivos.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestordearchivos.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var themeSelector: RadioGroup
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        themeSelector = findViewById(R.id.themeSelector)
        saveButton = findViewById(R.id.saveThemeButton)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val currentTheme = prefs.getString("app_theme", "guinda")

        if(currentTheme == "guinda"){
            themeSelector.check(R.id.guindaTheme)
        }else{
            themeSelector.check(R.id.azulTheme)
        }

        saveButton.setOnClickListener {
            val selectedTheme = when (themeSelector.checkedRadioButtonId) {
                R.id.guindaTheme -> "guinda"
                R.id.azulTheme -> "azul"
                else -> "guinda"
            }
            prefs.edit().putString("app_theme", selectedTheme).apply()
            Toast.makeText(this, "Tema guardado. Reinicia para aplicar los cambios.", Toast.LENGTH_SHORT).show()
        }
    }
}