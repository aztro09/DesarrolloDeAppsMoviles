package com.example.navegacionedifico1.p1

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.navegacionedifico1.fragments.LabFragment
import com.example.navegacionedifico1.fragments.SalonFragment
import com.example.navegacionedifico1.utils.isLab

class P1Activity : FragmentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P1Screen(onSalonClick = { salonNumber ->
                openSalonFragment(salonNumber)
            })
        }
    }

    private fun openSalonFragment(salonNumber: Int){
        val fragment = if(isLab(salonNumber)){
            LabFragment.newInstance(salonNumber)
        }else{
            SalonFragment.newInstance(salonNumber)
        }

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack(null)
            .commit()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun P1Screen(onSalonClick: (Int) -> Unit){
    Scaffold (
        topBar = {
            TopAppBar(title = {Text("Piso 1 (P1)")})
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(14) { index ->
                val salonNumber = index + 1101
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSalonClick(salonNumber) },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = "Salon $salonNumber",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}