package com.example.practica1.ui.selections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import com.example.practica1.R
import androidx.fragment.app.Fragment
import com.example.practica1.databinding.FragmentSelectionsBinding
class SelectionsFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_selections, container, false)

        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radio_group)
        val switch = view.findViewById<Switch>(R.id.switch_toggle)
        val result = view.findViewById<TextView>(R.id.text_result)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            result.text = if (isChecked) "CheckBox activado!" else "CheckBox desactivado"
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selected = view.findViewById<RadioButton>(checkedId)
            result.text = "Seleccionado: ${selected.text}"
        }

        switch.setOnCheckedChangeListener { _, isChecked ->
            result.text = if (isChecked) "Switch activado" else "Switch inactivo"
        }


        return view
    }
}