package com.example.gestordearchivos.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import com.example.gestordearchivos.utils.FileUtils
import com.example.gestordearchivos.R
import com.example.gestordearchivos.data.AppDatabase
import com.example.gestordearchivos.data.FavouriteFileEntity
import com.example.gestordearchivos.data.FileDAO
import com.example.gestordearchivos.data.RecentFileEntity
import com.example.gestordearchivos.ui.FileAdapter
import kotlinx.coroutines.launch

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

        findViewById<Button>(R.id.openFavouritesButton).setOnClickListener {
            startActivity(Intent(this, FavouritesActivity::class.java))
        }
        findViewById<Button>(R.id.openRecentsButton).setOnClickListener {
            startActivity(Intent(this, RecentsActivity::class.java))
        }
        val db = AppDatabase.getInstance(this)
        val dao = db.fileDao()

        pathText = findViewById(R.id.pathText)
        recyclerView = findViewById(R.id.fileRecyclerView)

        adapter = FileAdapter(
            onItemClick = { file ->
            if(file.isDirectory){
                val files = FileUtils.listFiles(this, file.uri)
                pathText.text = file.uri.path
            } else{
                val db = AppDatabase.getInstance(this)
                val dao = db.fileDao()

                lifecycleScope.launch{
                    dao.insertRecent(
                        RecentFileEntity(uri = file.uri.toString(),
                            name = file.name,
                            lastOpened = System.currentTimeMillis()
                        )
                    )
                }
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
        },
            onFavouriteClick = { file ->
                lifecycleScope.launch {
                    val isFav = dao.isFavourite(file.uri.toString())
                    if (isFav) {
                        dao.removeFavourite(file.uri.toString())
                        Toast.makeText(
                            this@MainActivity,
                            "Eliminado de favoritos",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        dao.addFavourite(
                            FavouriteFileEntity(
                                uri = file.uri.toString(),
                                name = file.name,
                                location = System.currentTimeMillis()
                            )
                        )
                        Toast.makeText(this@MainActivity, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        openFolderLauncher.launch(null)
    }
}
