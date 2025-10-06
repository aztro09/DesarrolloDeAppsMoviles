package com.example.f1adventure.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.f1adventure.R

class FragmentDetails : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        val name = activity?.intent?.getStringExtra("track_name")
        val location = activity?.intent?.getStringExtra("track_location")
        val length = activity?.intent?.getStringExtra("track_length")
        val turns = activity?.intent?.getIntExtra("track_turns", 0)
        val year = activity?.intent?.getIntExtra("track_year", 0)
        val driver = activity?.intent?.getStringExtra("track_driver")
        val info = activity?.intent?.getStringExtra("track_info")

        val textView = view.findViewById<TextView>(R.id.track_details)
        textView.text = """
            🏁 ${name}
            📍 Ubicación: $location
            📏 Longitud: ${length} km
            🔄 Curvas: $turns
            🗓️ Primera carrera: $year
            🧑‍✈️ Piloto destacado: $driver
            📝 Características: $info
        """.trimIndent()

        val trackImageRes = activity?.intent?.getIntExtra("track_image", R.drawable.default_track) ?: R.drawable.default_track
        val driverImageRes = activity?.intent?.getIntExtra("driver_image", R.drawable.default_driver) ?: R.drawable.default_driver

        val trackImageView = view.findViewById<ImageView>(R.id.track_image)
        val driverImageView = view.findViewById<ImageView>(R.id.driver_image)

        trackImageRes?.let { trackImageView.setImageResource(it) }
        driverImageRes?.let { driverImageView.setImageResource(it) }

        return view
    }
}