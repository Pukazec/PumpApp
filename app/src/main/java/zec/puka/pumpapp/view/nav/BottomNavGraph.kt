package zec.puka.pumpapp.view.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import zec.puka.pumpapp.view.BottomNavScreen
import zec.puka.pumpapp.view.main.AboutScreen
import zec.puka.pumpapp.view.main.MapScreen
import zec.puka.pumpapp.view.main.PumpScreen
import zec.puka.pumpapp.view.main.PumpState
import zec.puka.pumpapp.viewmodel.MapViewModel
import zec.puka.pumpapp.viewmodel.PumpViewModel

@ExperimentalPagingApi
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Movies.route
    ) {
        composable(route = BottomNavScreen.Movies.route) {
            val pumpViewModel = hiltViewModel<PumpViewModel>()
            PumpScreen(
                pumpsState = PumpState(pumpViewModel),
                onUpdate = { pumpViewModel.update(it.copy(liked = !it.liked)) },
                onDelete = { pumpViewModel.delete(it) }
            )
        }
        composable(route = BottomNavScreen.Map.route) {
            val mapViewModel = hiltViewModel<MapViewModel>()
            MapScreen(
                mapState = mapViewModel.mapState.value
            )
        }
        composable(route = BottomNavScreen.About.route) {
            AboutScreen()
        }
    }
}
