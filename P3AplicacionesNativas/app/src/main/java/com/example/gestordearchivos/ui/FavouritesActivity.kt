package com.example.gestordearchivos.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordearchivos.R
import com.example.gestordearchivos.data.AppDatabase
import com.example.gestordearchivos.data.FileDAO
import com.example.gestordearchivos.data.FileModel
import com.example.gestordearchivos.data.FavouriteFileEntity
import kotlinx.coroutines.launch

class FavouritesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FileAdapter
    private lateinit var pathText: TextView
    private lateinit var dao: FileDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        // Aplicar tema desde SharedPreferences
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val theme = prefs.getString("app_theme", "guinda")
        if (theme == "guinda") {
            setTheme(R.style.Theme_GestorDeArchivo_Guinda)
        } else {
            setTheme(R.style.Theme_GestorDeArchivo_Azul)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        recyclerView = findViewById(R.id.fileRecyclerView)
        pathText = findViewById(R.id.pathText)
        dao = AppDatabase.getInstance(this).fileDao()

        // Inicializar adaptador con onItemClick y onFavouriteClick
        adapter = FileAdapter(
            onItemClick = { file ->
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(file.uri, file.mimeType ?: "*/*")
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    startActivity(Intent.createChooser(intent, "Abrir con..."))
                } catch (e: Exception) {
                    Toast.makeText(this, "No se pudo abrir el archivo", Toast.LENGTH_SHORT).show()
                }
            },
            onFavouriteClick = { file ->
                lifecycleScope.launch {
                    val isFav = dao.isFavourite(file.uri.toString())
                    if (isFav) {
                        dao.removeFavourite(file.uri.toString())
                        Toast.makeText(this@FavouritesActivity, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
                        val updatedList = dao.getFavourites().map {
                            FileModel(it.name, Uri.parse(it.uri), false, 0L, 0L, null)
                        }
                        adapter.submitList(updatedList)
                    }
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Cargar favoritos desde la base de datos
        lifecycleScope.launch {
            val favourites = dao.getFavourites().map {
                FileModel(it.name, Uri.parse(it.uri), false, 0L, 0L, null)
            }
            adapter.submitList(favourites)
            pathText.text = "Favoritos"
        }
    }
}