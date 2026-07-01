package com.gateway.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gateway.android.data.repo.AuthRepository
import com.gateway.android.data.repo.DeviceRepository
import com.gateway.android.ui.developer.DeveloperScreen
import com.gateway.android.ui.health.DeviceHealthScreen
import com.gateway.android.ui.home.HomeScreen
import com.gateway.android.ui.links.CreateLinkScreen
import com.gateway.android.ui.links.PaymentLinksScreen
import com.gateway.android.ui.login.AuthScreen
import com.gateway.android.ui.payments.PaymentsScreen
import com.gateway.android.ui.setup.UpiSetupScreen
import com.gateway.android.ui.settings.SettingsScreen
import com.gateway.android.ui.theme.GatewayTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var deviceRepository: DeviceRepository

    private val activityScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (authRepository.isLoggedIn()) {
            activityScope.launch(Dispatchers.IO) {
                deviceRepository.registerDevice()
            }
        }

        val startDestination = when {
            !authRepository.isLoggedIn() -> "login"
            authRepository.needsUpiSetup() -> "upi_setup"
            else -> "home"
        }

        setContent {
            GatewayTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val backStack by navController.currentBackStackEntryAsState()

                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("login") { AuthScreen(navController) }
                        composable("upi_setup") { UpiSetupScreen(navController) }
                        composable("home") { HomeScreen(navController) }
                        composable("links") { PaymentLinksScreen(navController) }
                        composable("links/create") { CreateLinkScreen(navController) }
                        composable("payments") { PaymentsScreen(navController) }
                        composable("settings") { SettingsScreen(navController) }
                        composable("developer") { DeveloperScreen(navController) }
                        composable("health") { DeviceHealthScreen(navController) }
                    }
                }
            }
        }
    }
}
