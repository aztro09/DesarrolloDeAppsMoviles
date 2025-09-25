package com.example.f1adventure

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.f1adventure.fragments.FragmentTrack

class TrackActivity : AppCompatActivity(){
    override fun onCreate(savedInstaceState: Bundle?){
        super.onCreate(savedInstaceState)
        setContentView(R.layout.activity_track)

        supportFragmentManager.beginTransaction()
            .replace(R.id.track_container, FragmentTrack())
            .commit()
    }
}