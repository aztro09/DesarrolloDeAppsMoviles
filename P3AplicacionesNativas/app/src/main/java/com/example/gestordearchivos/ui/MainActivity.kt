package com.example.gestordearchivos.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Adapter
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordearchivos.utils.FileUtils

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
}
