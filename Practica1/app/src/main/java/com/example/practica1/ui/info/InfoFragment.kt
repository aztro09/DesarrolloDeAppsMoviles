package com.example.practica1.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Button
import com.example.practica1.R
import androidx.fragment.app.Fragment
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton

class InfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)

        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val button = view.findViewById<Button>(R.id.button_load)

        button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                progressBar.visibility = View.GONE
            }, 2000)
        }

        return view
    }
}