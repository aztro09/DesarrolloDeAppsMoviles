package com.example.practica1.ui.textfields

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
import com.example.practica1.databinding.FragmentTextfieldsBinding

class TextFieldsFragment : Fragment(){
    private var _binding: FragmentTextfieldsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_textfields, container, false)

        val editText = view.findViewById<EditText>(R.id.edit_text)
        val button = view.findViewById<Button>(R.id.button_show_text)
        val result = view.findViewById<TextView>(R.id.text_result)

        button.setOnClickListener{
            result.text = "Texto ingresado: ${editText.text}"
        }

        return view
    }
}