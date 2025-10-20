package com.example.f1adventure

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.f1adventure.fragments.FragmentTrack

class TrackAdapter(
    private val trackList: List<Track>,
    private val onItemClicked: (Track) -> Unit
): RecyclerView.Adapter<TrackAdapter.TrackViewHolder>(){
    class TrackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.track_name)
        val location: TextView = itemView.findViewById(R.id.track_location)
        val details: TextView = itemView.findViewById(R.id.track_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)

        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = trackList[position]
        holder.name.text = track.name
        holder.location.text = track.location
        holder.details.text = "Longitud ${track.lengthKm} kms | Curvas: ${track.turns} | Desde: ${track.firstRace} \n" +
                "Mejor piloto: ${track.bestDriver} \n ${track.characteristics}"
        holder.itemView.setOnClickListener {
            onItemClicked(track)
        }
    }

    override fun getItemCount(): Int = trackList.size
}

class TrackActivity : AppCompatActivity(){
    override fun onCreate(savedInstaceState: Bundle?){
        super.onCreate(savedInstaceState)
        setContentView(R.layout.activity_track)

        supportFragmentManager.beginTransaction()
            .replace(R.id.track_container, FragmentTrack())
            .commit()
    }
}