package com.example.gestordearchivos.ui

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ImageViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageView = ImageView(this)
        imageView.adjustViewBounds = true
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        setContentView(imageView)

        val uri = intent.getParcelableExtra<Uri>("fileUri")
        imageView.setImageURI(uri)
    }
}