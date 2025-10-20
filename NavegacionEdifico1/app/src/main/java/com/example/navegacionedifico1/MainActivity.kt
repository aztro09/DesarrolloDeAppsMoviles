package com.example.navegacionedifico1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.navegacionedifico1.ui.theme.NavegacionEdifico1Theme
import com.example.navegacionedifico1.pb.PbActivity
import com.example.navegacionedifico1.p1.P1Activity
import com.example.navegacionedifico1.p2.P2Activity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdificioMapScreen()
        }
    }

    @Composable
    fun EdificioMapScreen(){
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                painter = painterResource(id = R.drawable.Edificio1),
                contentDescription = "Mapa del Edifico 1",
                modifier = Modifier.fillMaxSize()
            )

            //Zona para click
            Box(
                modifier = Modifier
                    .offset(x = 172.dp, y = 44.dp)
                    .size(width = (576 - 172).dp, height = (657 - 44).dp)
                    .clickable {
                        // Navegar a la vista de Planta Baja
                        startActivity(Intent(this@MainActivity, PbActivity::class.java))
                    }

            )
        } }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EdificioNavigatorApp(){
        Scaffold (
            topBar = {
                TopAppBar(title = {Text("Edifico 1 ESCOM")})
            }
        ){ padding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Button(onClick = {
                    startActivity(Intent(this@MainActivity, PbActivity::class.java))
                }){
                    Text("Planta Baja (PB)")
                }
                Button(onClick = {
                    startActivity(Intent(this@MainActivity, P1Activity::class.java))
                }) {
                    Text("Piso 1 (P1)")
                }
                Button(onClick = {
                    startActivity(Intent(this@MainActivity, P2Activity::class.java))
                }) {
                    Text("Piso 2 (P2)")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavegacionEdifico1Theme {
        Greeting("Android")
    }
}