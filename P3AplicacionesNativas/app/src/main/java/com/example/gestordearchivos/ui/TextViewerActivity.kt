package com.example.gestordearchivos.ui

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TextViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
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