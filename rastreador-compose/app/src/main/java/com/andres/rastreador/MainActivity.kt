
package com.andres.rastreador

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andres.rastreador.ui.MainViewModel
import com.andres.rastreador.ui.HistoryViewModel
import com.andres.rastreador.ui.screens.HistoryScreen
import com.andres.rastreador.ui.screens.MainScreen
import com.andres.rastreador.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    private val mainVM by viewModels<MainViewModel>()
    private val histVM by viewModels<HistoryViewModel>()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestInitialPermissions()

        setContent {
            val theme = mainVM.theme.value
            AppTheme(themeName = theme) {
                val nav = rememberNavController()
                NavHost(navController = nav, startDestination = "main") {
                    composable("main") {
                        MainScreen(vm = mainVM, onOpenHistory = { nav.navigate("history") })
                    }
                    composable("history") {
                        HistoryScreen(vm = mainVM, hvm = histVM, onBack = { nav.popBackStack() })
                    }
                }
            }
        }
    }

    private fun requestInitialPermissions() {
        val perms = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= 33) {
            perms += Manifest.permission.POST_NOTIFICATIONS
        }
        if (Build.VERSION.SDK_INT >= 29) {
            perms += Manifest.permission.ACCESS_BACKGROUND_LOCATION
        }
        permissionLauncher.launch(perms.toTypedArray())
    }
}
