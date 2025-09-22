package com.example.practica1.ui.buttons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.practica1.R
import androidx.fragment.app.Fragment
class ButtonsFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buttons, container, false)

        val button = view.findViewById<Button>(R.id.button_click)
        val imageButton = view.findViewById<ImageButton>(R.id.image_button)
        val result = view.findViewById<TextView>(R.id.text_result)

        button.setOnClickListener {
            result.text = "Boton presionado!"
        }

        imageButton.setOnClickListener {
            result.text = "ImageButton presionado!"
        }

        return view
    }
}