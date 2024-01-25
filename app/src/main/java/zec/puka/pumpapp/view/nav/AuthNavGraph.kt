package zec.puka.pumpapp.view.nav

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import zec.puka.pumpapp.R
import zec.puka.pumpapp.view.AuthNavScreen
import zec.puka.pumpapp.view.auth.AuthenticationScreen
import zec.puka.pumpapp.viewmodel.AuthenticationViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    authenticationViewModel: AuthenticationViewModel,
    context: Context
) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthNavScreen.Login.route
    ) {

        composable(route = AuthNavScreen.Login.route) {
            AuthenticationScreen(
                icon = R.drawable.login,
                onLogin = {
                    authenticationViewModel.login(
                        onSuccess = {
                            navController.popBackStack()
                            // login and enter
                            navController.navigate(route = Graph.MAIN)
                        },
                        onFail = {
                            Toast.makeText(
                                context,
                                context.getString(R.string.unable_to_login), Toast.LENGTH_SHORT
                            ).show()
                        }
                    )


                },
                onRegister = {
                    navController.popBackStack()
                    // goto register
                    navController.navigate(route = AuthNavScreen.Register.route)
                },
                authenticationState = authenticationViewModel.authenticationState.value,
                onEmailChanged = { authenticationViewModel.onEmailChanged(it) },
                onPasswordChanged = { authenticationViewModel.onPasswordChanged(it) }
            )
        }
        composable(route = AuthNavScreen.Register.route) {
            AuthenticationScreen(
                isLogin = false,
                icon = R.drawable.register,
                onLogin = {
                    navController.popBackStack()
                    // goto login
                    navController.navigate(route = AuthNavScreen.Login.route)
                },
                onRegister = {

                    authenticationViewModel.register(
                        onSuccess = {
                            navController.popBackStack()
                            // register and enter
                            navController.navigate(route = Graph.MAIN)
                        },
                        onFail = {
                            Toast.makeText(
                                context,
                                context.getString(R.string.unable_to_register), Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                },
                authenticationState = authenticationViewModel.authenticationState.value,
                onEmailChanged = { authenticationViewModel.onEmailChanged(it) },
                onPasswordChanged = { authenticationViewModel.onPasswordChanged(it) }

            )
        }
    }
}