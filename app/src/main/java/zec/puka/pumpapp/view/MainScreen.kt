package zec.puka.pumpapp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import zec.puka.pumpapp.view.nav.BottomNavGraph


@ExperimentalPagingApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold( // smartest
        bottomBar = { BottomBar (navController = navController) }
    ) {
        BottomNavGraph(navController = navController)
    }
}


@Composable
fun BottomBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar {
        BottomNavScreen::class
            .sealedSubclasses
            .reversed()
            .forEach {
                AddItem(
                    screen = it.objectInstance!!,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem( // RowScope.!!!
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = stringResource(id = screen.title)
            )
        },
        label = { Text(text = stringResource(id = screen.title)) },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true, // T, F or NULL
        onClick = {
            navController.navigate(route = screen.route) {
                popUpTo(id = navController.graph.startDestinationId)
                launchSingleTop = true
            }
        })
}