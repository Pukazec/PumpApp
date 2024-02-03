package zec.puka.pumpapp.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.ui.graphics.vector.ImageVector
import zec.puka.pumpapp.R

sealed class BottomNavScreen (
    val route: String,
    val title: Int,
    val icon: ImageVector
) {
    object Cats: BottomNavScreen(
        route = "cats",
        title = R.string.cats,
        icon = Icons.Default.Pets
    )
    object Map: BottomNavScreen(
        route = "map",
        title = R.string.map,
        icon = Icons.Default.Map
    )
    object About: BottomNavScreen(
        route = "about",
        title = R.string.about,
        icon = Icons.Default.Person
    )
}