package com.carlossaulvillabonapinilla.lopify.ui.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.carlossaulvillabonapinilla.lopify.ui.screens.HistorialScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.HomeScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.LoginScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.LoopifySplashScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.MapScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.MetasScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.PerfilScreen
import com.carlossaulvillabonapinilla.lopify.ui.screens.RegisterScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import com.carlossaulvillabonapinilla.lopify.viewmodel.AuthState
import com.carlossaulvillabonapinilla.lopify.viewmodel.AuthViewModel
import com.carlossaulvillabonapinilla.lopify.viewmodel.RegisterViewModel

object Routes {
    const val SPLASH    = "splash"
    const val LOGIN     = "login"
    const val REGISTER  = "register"
    const val HOME      = "home"
    const val MAP       = "map"
    const val METAS     = "metas"
    const val PERFIL    = "perfil"
    const val HISTORIAL = "historial"
}

@Composable
fun LoopifyNavGraph() {

    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

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
                        popUpTo(0) { inclusive = true }
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

            val registerViewModel: RegisterViewModel = viewModel()

            RegisterScreen(
                viewModel = registerViewModel,
                onBackClick = { navController.popBackStack() },
                onLoginClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {

            var selectedNavIndex by remember { mutableStateOf(0) }

            HomeScreen(
                viewModel = authViewModel,
                selectedNavIndex = selectedNavIndex,
                onNavItemSelected = { index ->
                    selectedNavIndex = index
                    when (index) {
                        1 -> navController.navigate(Routes.HISTORIAL)
                        2 -> navController.navigate(Routes.METAS)
                        3 -> navController.navigate(Routes.MAP)
                        4 -> navController.navigate(Routes.PERFIL)
                    }
                }
            )
        }

        composable(Routes.HISTORIAL) {

            var selectedNavIndex by remember { mutableStateOf(1) }

            HistorialScreen(
                selectedNavIndex = selectedNavIndex,
                onNavItemSelected = { index ->
                    selectedNavIndex = index
                    when (index) {
                        0 -> navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                        1 -> { }
                        2 -> navController.navigate(Routes.METAS)
                        3 -> navController.navigate(Routes.MAP)
                        4 -> navController.navigate(Routes.PERFIL)
                    }
                },
                onNavigateToHome = { navController.popBackStack() }
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
                        1 -> navController.navigate(Routes.HISTORIAL)
                        2 -> navController.navigate(Routes.METAS)
                        3 -> { }
                        4 -> navController.navigate(Routes.PERFIL)
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
                        1 -> navController.navigate(Routes.HISTORIAL)
                        2 -> { }
                        3 -> navController.navigate(Routes.MAP)
                        4 -> navController.navigate(Routes.PERFIL)
                    }
                },
                onNavigateToHome = { navController.popBackStack() }
            )
        }

        composable(Routes.PERFIL) {

            var selectedNavIndex by remember { mutableStateOf(4) }

            PerfilScreen(
                viewModel = authViewModel,
                selectedNavIndex = selectedNavIndex,
                onNavItemSelected = { index ->
                    selectedNavIndex = index
                    when (index) {
                        0 -> navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                        1 -> navController.navigate(Routes.HISTORIAL)
                        2 -> navController.navigate(Routes.METAS)
                        3 -> navController.navigate(Routes.MAP)
                        4 -> { }
                    }
                },
                onNavigateToHome = { navController.popBackStack() }
            )
        }
    }
}