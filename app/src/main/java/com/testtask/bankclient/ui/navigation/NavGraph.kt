package com.testtask.bankclient.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.testtask.bankclient.ui.screen.home.HomeScreen
import com.testtask.bankclient.ui.screen.purchases.PurchasesScreen
import com.testtask.bankclient.ui.screen.registration.RegistrationScreen

sealed class Screen(val route: String) {
    data object Home: Screen("home")
    data object Registration: Screen("registration")
    data object Purchases: Screen("purchases ")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Registration.route) {
            RegistrationScreen(navController)
        }
        composable(Screen.Purchases.route) {
            PurchasesScreen(navController)
        }
    }
}