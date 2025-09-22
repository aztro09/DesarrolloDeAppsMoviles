package com.example.practica1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.example.practica1.R
import com.example.practica1.databinding.FragmentHomeBinding
import com.example.practica1.ui.buttons.ButtonsFragment
import com.example.practica1.ui.info.InfoFragment
import com.example.practica1.ui.selections.SelectionsFragment
import com.example.practica1.ui.textfields.TextFieldsFragment

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       val view = inflater.inflate(R.layout.fragment_home, container, false)

       /* view.findViewById<Button>(R.id.btn_textfields).setOnClickListener {
            navigateTo(TextFieldsFragment())
        }
        view.findViewById<Button>(R.id.btn_buttons).setOnClickListener {
            navigateTo(ButtonsFragment())
        }
        view.findViewById<Button>(R.id.btn_info).setOnClickListener {
            navigateTo(InfoFragment())
        }
        view.findViewById<Button>(R.id.btn_lists).setOnClickListener {
            navigateTo(ListFragment())
        }
        view.findViewById<Button>(R.id.btn_selections).setOnClickListener {
            navigateTo(SelectionsFragment())
        }*/

        return view
    }

    private fun navigateTo(fragment: Fragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, fragment)
            .commit()
    }
}