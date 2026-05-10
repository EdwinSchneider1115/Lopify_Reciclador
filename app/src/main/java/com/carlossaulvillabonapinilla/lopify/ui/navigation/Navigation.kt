package com.carlossaulvillabonapinilla.lopify.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.carlossaulvillabonapinilla.lopify.ui.screens.HomeScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.LoginScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.LoopifySplashScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.MapScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.MetasScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.RegisterScreen

object Routes {
    const val SPLASH   = "splash"
    const val LOGIN    = "login"
    const val REGISTER = "register"
    const val HOME     = "home"
    const val MAP      = "map"
    const val METAS    = "metas"
}

@Composable
fun LoopifyNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            LoopifySplashScreen(
                onSplashFinished = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onGoogleClick = { },
                onFacebookClick = { },
                onForgotPassword = { },
                onRegister = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onBackClick = { navController.popBackStack() },
                onRegisterClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) { inclusive = false }
                    }
                },
                onLoginClick = { navController.popBackStack() }
            )
        }

        composable(Routes.HOME) {
            var selectedNavIndex by remember { mutableStateOf(0) }
            HomeScreen(
                selectedNavIndex = selectedNavIndex,
                onNavItemSelected = { index ->
                    selectedNavIndex = index
                    when (index) {
                        2 -> navController.navigate(Routes.METAS)
                        3 -> navController.navigate(Routes.MAP)
                    }
                }
            )
        }

        composable(Routes.MAP) {
            var selectedNavIndex by remember { mutableStateOf(3) }
            MapScreen(
                selectedNavIndex = selectedNavIndex,
                onNavItemSelected = { index ->
                    selectedNavIndex = index
                    when (index) {
                        0 -> navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                        2 -> navController.navigate(Routes.METAS)
                        3 -> { }
                    }
                },
                onNavigateToHome = { navController.popBackStack() }
            )
        }

        composable(Routes.METAS) {
            var selectedNavIndex by remember { mutableStateOf(2) }
            MetasScreen(
                selectedNavIndex = selectedNavIndex,
                onNavItemSelected = { index ->
                    selectedNavIndex = index
                    when (index) {
                        0 -> navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                        2 -> { }
                        3 -> navController.navigate(Routes.MAP)
                    }
                },
                onNavigateToHome = { navController.popBackStack() }
            )
        }
    }
}