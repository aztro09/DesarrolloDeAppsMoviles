package com.example.gestordearchivos.ui

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import com.example.gestordearchivos.utils.FileUtils
import com.example.gestordearchivos.R
import com.example.gestordearchivos.data.AppDatabase
import com.example.gestordearchivos.data.FavouriteFileEntity
import kotlinx.coroutines.launch
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {
   private lateinit var recyclerView: RecyclerView
   private lateinit var adapter: FileAdapter
   private lateinit var pathText: TextView

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openFolderLauncher.launch(null)
        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                openFolderLauncher.launch(null)
            } else {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.data = Uri.parse("package:$packageName")
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    AlertDialog.Builder(this)
                        .setTitle("Permiso requerido")
                        .setMessage("Para que la app funcione correctamente, ve a:\n\nAjustes > Aplicaciones > Gestor de Archivos > Permisos > Activar 'Acceso a todos los archivos'.")
                        .setPositiveButton("Entendido", null)
                        .show()
                }
            }
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private val openFolderLauncher = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()){ uri ->
       if(uri != null){
           contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
           val files = FileUtils.listFiles(this, uri)
           adapter.submitList(files)
           pathText.text = uri.path
       }
   }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_favourite -> {
                startActivity(Intent(this, FavouritesActivity::class.java))
                true
            } R.id.action_recents -> {
                startActivity(Intent(this, RecentsActivity::class.java))
                true
            }R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val theme = prefs.getString("app_theme", "guinda")

        if (theme == "guinda"){
            setTheme(R.style.Theme_GestorDeArchivo_Guinda)
        } else{
            setTheme(R.style.Theme_GestorDeArchivo_Azul)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkStoragePermission()

        findViewById<Button>(R.id.openFavouritesButton).setOnClickListener {
            startActivity(Intent(this, FavouritesActivity::class.java))
        }
        findViewById<Button>(R.id.openRecentsButton).setOnClickListener {
            startActivity(Intent(this, RecentsActivity::class.java))
        }
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.mainToolbar)
        setSupportActionBar(toolbar)

        val db = AppDatabase.getInstance(this)
        val dao = db.fileDao()

        pathText = findViewById(R.id.pathText)
        recyclerView = findViewById(R.id.fileRecyclerView)

        adapter = FileAdapter (
            onItemClick = { file ->
                if (file.isDirectory) {
                    val files = FileUtils.listFiles(this, file.uri)
                    adapter.submitList(files)
                    pathText.text = file.uri.path
                } else {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(file.uri, file.mimeType)
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    startActivity(Intent.createChooser(intent, "Abrir con..."))
                }
            }, onFavouriteClick = { file ->
            lifecycleScope.launch {
                val isFav = dao.isFavourite(file.uri.toString())
                if (isFav) {
                    dao.removeFavourite(file.uri.toString())
                    Toast.makeText(
                        this@MainActivity,
                        "Eliminado de favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }else {
                    dao.addFavourite(
                        FavouriteFileEntity(
                            uri = file.uri.toString(),
                            name = file.name,
                            location = System.currentTimeMillis()
                        )
                    )
                    Toast.makeText(this@MainActivity, "Añadido a favoritos", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        openFolderLauncher.launch(null)
    }
}
