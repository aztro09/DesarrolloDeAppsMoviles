package com.example.f1adventure.fragments
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.f1adventure.R
import androidx.fragment.app.Fragment
import com.example.f1adventure.TrackActivity

class FragmentMenu : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val button = view.findViewById<Button>(R.id.btn_start)
        button.setOnClickListener {
            startActivity(Intent(activity, TrackActivity::class.java))
        }

        return view
    }
}