package com.example.f1adventure.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f1adventure.DetailActivity
import com.example.f1adventure.R
import com.example.f1adventure.Track
import com.example.f1adventure.TrackAdapter

class FragmentTrack : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrackAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_track, container, false)

        recyclerView = view.findViewById(R.id.recycler_tracks)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val tracks = listOf(
            Track("Spa-Francorchamps","Bélgica",
                7.004,
                19,
                1950,
                "Clima impredecible, curvas icónicas como Eau Rouge.",
                "Michael Schumacher",
                R.drawable.spa,
                R.drawable.schumacher
            ),
            Track("Monza", "Italia", 5.793, 11, 1950, "Rectas largas, curvas rápidas como Parabólica.", "Lewis Hamilton",
                R.drawable.monza, R.drawable.hamilton),
            Track("Suzuka", "Japón", 5.807, 18, 1987, "Trazado en forma de 8, curvas técnicas como 130R.", "Sebastian Vettel",
                R.drawable.suzuka, R.drawable.vettel),
            Track("Monaco", "Mónaco", 3.337, 19, 1950, "Calles estrechas, túnel, curvas cerradas.", "Ayrton Senna",
                R.drawable.monaco, R.drawable.senna),
            Track("Silverstone", "Reino Unido", 5.891, 18, 1950, "Curvas rápidas como Maggots y Becketts.", "Lewis Hamilton",
                R.drawable.silverstone, R.drawable.hamilton),
            Track("Nürburgring", "Alemania", 5.148, 15, 1951, "Clima cambiante, curvas técnicas.", "Michael Schumacher",
                R.drawable.nurburgring, R.drawable.schumacher)
        )

        adapter = TrackAdapter(tracks) { track ->
            val intent = Intent(activity, DetailActivity::class.java).apply {
                putExtra("track_name", track.name)
                putExtra("track_location", track.location)
                putExtra("track_length", track.lengthKm)
                putExtra("track_turns", track.turns)
                putExtra("track_year", track.firstRace)
                putExtra("track_driver", track.bestDriver)
                putExtra("track_info", track.characteristics)
                putExtra("track_image", track.trackImageRes)
                putExtra("driver_image", track.driverImageRes)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        return view
    }
}