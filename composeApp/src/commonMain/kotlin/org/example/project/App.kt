package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.project.screens.detail.Detail
import org.example.project.screens.detail.DetailScreen
import org.example.project.screens.detail.DetailViewModel
import org.example.project.screens.home.Home
import org.example.project.screens.home.HomeScreen
import org.example.project.screens.login.Login
import org.example.project.screens.login.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    CmpTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Login) {
            composable<Login> {
                LoginScreen({
                    navController.navigate(Home)
                })
            }
            composable<Home> {
                HomeScreen({
                    navController.navigate(Detail(it.id))
                })
            }
            composable<Detail> { backStackEntry ->
                val detail = backStackEntry.toRoute<Detail>()
                DetailScreen(
                    viewModel = DetailViewModel(detail.id),
                    onBack = { navController.popBackStack() })
            }
        }
    }
}