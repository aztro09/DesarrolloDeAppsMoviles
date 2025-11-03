package com.example.f1adventure

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.f1adventure.fragments.FragmentDetails
import com.example.f1adventure.fragments.FragmentMenu
import com.example.f1adventure.fragments.FragmentTrack
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.nav_view)
        //fragments
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, FragmentMenu())
            .commit()

        navView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId){
                R.id.nav_home -> FragmentMenu()
                R.id.nav_tracks -> FragmentTrack()
                R.id.nav_details -> FragmentDetails()
                else -> null
            }

            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, it)
                    .commit()
                true
            } ?: false
        }
    }

}

