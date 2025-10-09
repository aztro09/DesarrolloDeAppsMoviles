package com.example.gestordearchivos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordearchivos.data.FileModel
import com.example.gestordearchivos.R

class FileAdapter(
    private val onItemClick: (FileModel) -> Unit,
    private val onFavouriteClick: (FileModel) -> Unit
) : ListAdapter<FileModel, FileAdapter.FileViewHolder>(DiffCallback()){

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val icon: ImageView = itemView.findViewById(R.id.fileIcon)
        val name: TextView = itemView.findViewById(R.id.fileName)
        val favouriteButton: ImageButton = itemView.findViewById(R.id.favouriteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)

        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = getItem(position)
        holder.name.text = file.name
        holder.icon.setImageResource(
            if (file.isDirectory) R.drawable.ic_folder_black else R.drawable.ic_file_black
        )
        holder.itemView.setOnClickListener { onItemClick(file) }
        holder.favouriteButton.setOnClickListener { onFavouriteClick(file) }
    }

    class DiffCallback : DiffUtil.ItemCallback<FileModel>(){
        override fun areItemsTheSame(oldItem: FileModel, newItem: FileModel) = oldItem.uri == newItem.uri
        override fun areContentsTheSame(oldItem: FileModel, newItem: FileModel) = oldItem == newItem
    }
}