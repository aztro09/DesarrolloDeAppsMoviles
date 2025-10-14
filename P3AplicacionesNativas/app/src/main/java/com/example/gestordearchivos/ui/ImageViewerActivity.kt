package com.example.gestordearchivos.ui

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.gestordearchivos.R

class ImageViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val theme = prefs.getString("app_theme", "guinda")

        if (theme == "guinda") {
            setTheme(R.style.Theme_GestorDeArchivo_Guinda)
        }else{
            setTheme(R.style.Theme_GestorDeArchivo_Azul)
        }
        super.onCreate(savedInstanceState)
        val imageView = ImageView(this)
        imageView.adjustViewBounds = true
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        setContentView(imageView)

        val uri = intent.getParcelableExtra<Uri>("fileUri")
        imageView.setImageURI(uri)
    }
}