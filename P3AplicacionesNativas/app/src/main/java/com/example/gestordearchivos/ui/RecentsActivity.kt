package com.example.gestordearchivos.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Adapter
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import com.example.gestordearchivos.data.AppDatabase
import com.example.gestordearchivos.data.RecentFileEntity
import com.example.gestordearchivos.R
import com.example.gestordearchivos.data.FileDAO
import com.example.gestordearchivos.data.FileModel
import kotlinx.coroutines.launch


class RecentsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FileAdapter
    private lateinit var pathText: TextView
    private lateinit var dao: FileDAO

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_recents)

        recyclerView = findViewById(R.id.fileRecyclerView)
        pathText = findViewById(R.id.pathText)
        adapter = FileAdapter{ file ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(file.uri, file.mimeType)
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivity(Intent.createChooser(intent, "Abrir con..."))
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        dao = AppDatabase.getInstance(this).fileDao()

        lifecycleScope.launch {
            val recents = dao.getRecentFiles().map {
                FileModel(it.name, Uri.parse(it.uri), false, 0L, it.lastOpened, null)
            }
            adapter.submitList(recents)
            pathText.text = "Recientes"
        }
    }
}