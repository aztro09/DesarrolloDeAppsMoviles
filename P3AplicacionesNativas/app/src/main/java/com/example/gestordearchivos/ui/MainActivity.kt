package com.example.gestordearchivos.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Adapter
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordearchivos.utils.FileUtils
import com.example.gestordearchivos.R
import com.example.gestordearchivos.ui.FileAdapter

class MainActivity : ComponentActivity() {
   private lateinit var recyclerView: RecyclerView
   private lateinit var adapter: FileAdapter
   private lateinit var pathText: TextView

   private val openFolderLauncher = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()){ uri ->
       if(uri != null){
           contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
           val files = FileUtils.listFiles(this, uri)
           adapter.submitList(files)
           pathText.text = uri.path
       }
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pathText = findViewById(R.id.pathText)
        recyclerView = findViewById(R.id.fileRecyclerView)
        adapter = FileAdapter { file ->
            if(file.isDirectory){
                val files = FileUtils.listFiles(this, file.uri)
                pathText.text = file.uri.path
            } else{
                when (file.mimeType){
                    "text/plain", "application/json", "application/xml" -> {
                        val intent = Intent(this, TextViewerActivity::class.java)
                        intent.putExtra("fileUri", file.uri)
                        startActivity(intent)
                    }
                    "image/jpeg", "image/png", "image/gif" -> {
                        val intent = Intent(this, ImageViewerActivity::class.java)
                        intent.putExtra("fileUri", file.uri)
                        startActivity(intent)
                    }
                    else -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setDataAndType(file.uri, file.mimeType)
                        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        startActivity(Intent.createChooser(intent, "Abrir con..."))
                    }
                }
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        openFolderLauncher.launch(null)
    }
}
