package com.example.f1adventure

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.f1adventure.fragments.FragmentDetails

class DetailActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportFragmentManager.beginTransaction()
            .replace(R.id.detail_container, FragmentDetails())
            .commit()
    }
}