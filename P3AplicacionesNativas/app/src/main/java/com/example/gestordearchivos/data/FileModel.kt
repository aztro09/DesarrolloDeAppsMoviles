package com.example.gestordearchivos.data

import android.net.Uri
import java.net.URI

data class FileModel(
    val name: String,
    val uri: Uri,
    val isDirectory: Boolean,
    val size: Long,
    val lastModified: Long,
    val mimeType: String?

)
