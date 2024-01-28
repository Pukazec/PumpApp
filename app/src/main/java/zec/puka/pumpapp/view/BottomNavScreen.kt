package zec.puka.pumpapp.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.ui.graphics.vector.ImageVector
import zec.puka.pumpapp.R

sealed class BottomNavScreen ( // discuss sealed class
    val route: String, // must not be in resources!
    val title: Int, // no primitives - discuss, resources
    val icon: ImageVector
) { // now Tools -> Kotlin -> Show Kotlin Bytecode -> it is abstract class with private constructor, so only inner classes can extend! WOW TRICK!!!
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
    ) // static inner classes -> java does not support top level static classes! // explain why static!!!
}