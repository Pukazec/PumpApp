package zec.puka.pumpapp.view.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import zec.puka.pumpapp.MainActivity
import zec.puka.pumpapp.view.MainScreen
import zec.puka.pumpapp.viewmodel.AuthenticationViewModel

@ExperimentalPagingApi
@Composable
fun RootNavGraph(navController: NavHostController) {
    val authenticationViewModel = viewModel<AuthenticationViewModel>()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.MAIN) {

        authNavGraph(
            navController = navController,
            authenticationViewModel = authenticationViewModel,
            context = context)

        composable(route = Graph.MAIN) {
            MainScreen()
        }
    }

}