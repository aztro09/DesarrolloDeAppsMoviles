package com.example.gestordearchivos.utils

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.example.gestordearchivos.data.FileModel
import java.net.URI

object FileUtils{
    fun listFiles(context: Context, uri: Uri): List<FileModel>{
        val docFile = DocumentFile.fromTreeUri(context, uri)
        return docFile?.listFiles()?.map{
            FileModel(
                name = it.name ?: "Sin nombre",
                uri = it.uri,
                isDirectory = it.isDirectory,
                size = it.length(),
                lastModified = it.lastModified(),
                mimeType = it.type
            )
        } ?: emptyList()
    }
}