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
import zec.puka.pumpapp.view.main.CatScreen
import zec.puka.pumpapp.view.main.CatState
import zec.puka.pumpapp.viewmodel.MapViewModel
import zec.puka.pumpapp.viewmodel.CatViewModel

@ExperimentalPagingApi
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Cats.route
    ) {
        composable(route = BottomNavScreen.Cats.route) {
            val catViewModel = hiltViewModel<CatViewModel>()
            CatScreen(
                catState = CatState(catViewModel),
                onUpdate = { catViewModel.update(it.copy(liked = !it.liked)) },
                onDelete = { catViewModel.delete(it) }
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
