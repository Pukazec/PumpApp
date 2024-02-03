package zec.puka.pumpapp.view.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import zec.puka.pumpapp.R
import zec.puka.pumpapp.view.common.AnimatedIconButton
import zec.puka.pumpapp.view.nav.Graph
import zec.puka.pumpapp.viewmodel.AuthenticationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    authenticationState: AuthenticationState,
    icon: Int,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLogin: Boolean = true,
    authenticationViewModel: AuthenticationViewModel,
    navController: NavHostController,
) {

    val localContext = LocalContext.current

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    val visibleState = remember {
        MutableTransitionState(initialState = false).apply {
            targetState = true
        }
    }

    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        authenticationViewModel.handleGoogleSignInResult(
            data = result.data,
            onSuccess = {
                navController.popBackStack()
                navController.navigate(route = Graph.MAIN) },
            onFail = {
                navController.popBackStack()
                navController.navigate(route = Graph.MAIN)

//              Toast.makeText(
//                context,
//                context.getString(R.string.unable_to_register), Toast.LENGTH_SHORT
//            ).show()
            },
        )
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn() + slideInHorizontally(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = stringResource(R.string.authentication)
            )
            Card(
                modifier = modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                shape = RoundedCornerShape(size = 9.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                Column(
                    modifier = modifier
                        .background(color = MaterialTheme.colorScheme.surface)
                        .padding(all = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(space = 9.dp)
                ) {

                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = authenticationState.email,
                        onValueChange = onEmailChanged,
                        label = { Text(text = stringResource(R.string.email_address)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        isError = !authenticationState.isEmailValid
                    )
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = authenticationState.password,
                        onValueChange = onPasswordChanged,
                        label = { Text(text = stringResource(R.string.password)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        isError = !authenticationState.isPasswordValid,
                        visualTransformation = PasswordVisualTransformation()
                    )

                    AnimatedIconButton(
                        onClick = if (isLogin) onLogin else onRegister,
                        text = {
                            Text(
                                text = if (isLogin)
                                    stringResource(R.string.login) else stringResource(
                                    R.string.register
                                )
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = stringResource(R.string.person)
                            )
                        },
                        modifier = modifier.align(Alignment.End),
                        enabled = authenticationState.isEmailValid && authenticationState.isPasswordValid,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    )
                    AnimatedIconButton(
                        modifier = modifier.align(Alignment.End),
                        onClick = if (isLogin) onRegister else onLogin,
                        text = {
                            Text(
                                text = if (isLogin)
                                    stringResource(R.string.register) else stringResource(
                                    R.string.login
                                )
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = stringResource(R.string.person)
                            )
                        }
                    )

                    Button(
                        modifier = modifier.align(Alignment.End).padding(0.dp),
                        onClick = { authenticationViewModel.googleSignIn(activityResultLauncher, localContext) },) {
                        Image(painter = painterResource(R.drawable.google), contentDescription = "Sign in with Google")
                    }
                }
            }
        }
    }
}