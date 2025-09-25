package com.example.practica1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.example.practica1.R
import com.google.android.material.tabs.TabLayoutMediator
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.practica1.ui.buttons.ButtonsFragment
import com.example.practica1.ui.info.InfoFragment
import com.example.practica1.ui.lists.ListsFragment
import com.example.practica1.ui.selections.SelectionsFragment
import com.example.practica1.ui.textfields.TextFieldsFragment


class SecondActivity : AppCompatActivity() {

    private val titles = listOf("TextFields", "Botones", "Selecciones", "Listas", "Informacion")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)

        viewPager.adapter = UIElementsAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    // Adaptador interno
    private inner class UIElementsAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = titles.size

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> TextFieldsFragment()
                1 -> ButtonsFragment()
                2 -> SelectionsFragment()
                3 -> ListsFragment()
                4 -> InfoFragment()
                else -> Fragment() // Fallback
            }
        }
    }
}