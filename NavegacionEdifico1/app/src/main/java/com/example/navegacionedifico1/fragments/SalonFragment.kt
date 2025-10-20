package com.example.navegacionedifico1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment

class SalonFragment : Fragment() {
    private var salonNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            salonNumber = it.getInt("salonNumber")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SalonScreen(salonNumber)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SalonScreen(salonNumber: Int){
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(" $salonNumber") })
            }
        ) { padding ->
            Column(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Información del $salonNumber", style = MaterialTheme.typography.headlineSmall
                )
                Text("Ubicación: Planta Baja")
                Divider()
                Text("Ubicación: Según piso")
                Text("Capacidad: 30 personas")
                Text("Equipamiento: Proyector, pizarrón, aire acondicionado")
                Text("Uso: Clases teóricas, presentaciones, reuniones")
            }
        }
    }

    companion object {
        fun newInstance(salonNumber: Int): SalonFragment {
            val fragment = SalonFragment()
            val args = Bundle()
            args.putInt("salonNumber", salonNumber)
            fragment.arguments = args
            return fragment
        }
    }
}