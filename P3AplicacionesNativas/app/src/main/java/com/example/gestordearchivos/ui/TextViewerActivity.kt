package com.example.gestordearchivos.ui

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gestordearchivos.R

class TextViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val theme = prefs.getString("app_theme", "guinda")

        if (theme == "guinda") {
            setTheme(R.style.Theme_GestorDeArchivo_Guinda)
        }else{
            setTheme(R.style.Theme_GestorDeArchivo_Azul)
        }
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        setContentView(textView)

        val uri = intent.getParcelableExtra<Uri>("fileUri")
        uri?.let{
            val inputStream = contentResolver.openInputStream(it)
            val text = inputStream?.bufferedReader().use {reader -> reader?.readText()}
            textView.text = text ?: "No se pudo leer el archivo"
        }
    }
}