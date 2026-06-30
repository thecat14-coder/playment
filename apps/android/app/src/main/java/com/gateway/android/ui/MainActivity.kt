package com.gateway.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gateway.android.data.repo.AuthRepository
import com.gateway.android.ui.home.HomeScreen
import com.gateway.android.ui.login.LoginScreen
import com.gateway.android.ui.feed.PaymentFeedScreen
import com.gateway.android.ui.diagnostics.DiagnosticsScreen
import com.gateway.android.ui.settings.SettingsScreen
import com.gateway.android.ui.theme.GatewayTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startDestination = if (authRepository.isLoggedIn()) "home" else "login"
        setContent {
            GatewayTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("login") { LoginScreen(navController) }
                        composable("home") { HomeScreen(navController) }
                        composable("feed") { PaymentFeedScreen(navController) }
                        composable("diagnostics") { DiagnosticsScreen() }
                        composable("settings") { SettingsScreen(navController) }
                    }
                }
            }
        }
    }
}
