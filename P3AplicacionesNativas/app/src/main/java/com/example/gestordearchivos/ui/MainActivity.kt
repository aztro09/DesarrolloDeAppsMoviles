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

class MainActivity : ComponentActivity() {
   private lateinit var recyclerView: RecyclerView
   private lateinit var adapter: Adapter
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
        adapter = FileAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        openFolderLauncher.launch(null)
    }
}
