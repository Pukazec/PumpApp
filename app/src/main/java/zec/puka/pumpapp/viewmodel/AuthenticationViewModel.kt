package zec.puka.pumpapp.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Patterns
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import zec.puka.pumpapp.repository.AuthenticationRepository
import zec.puka.pumpapp.utils.isValidPassword
import zec.puka.pumpapp.view.auth.AuthenticationState

class AuthenticationViewModel : ViewModel() {

    private val _authenticationState = mutableStateOf(
        AuthenticationState()
    )
    val authenticationState : State<AuthenticationState>
        get() = _authenticationState

    fun onEmailChanged(email: String) {
        _authenticationState.value = _authenticationState.value.copy(
            email = email,
            isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        )
    }
    fun onPasswordChanged(password: String) {
        _authenticationState.value = _authenticationState.value.copy(
            password = password,
            isPasswordValid = password.isValidPassword()
        )
    }

    fun login(
        onSuccess: () -> Unit,
        onFail: () -> Unit,
    ) {
        AuthenticationRepository.login(
            email = authenticationState.value.email,
            password = authenticationState.value.password,
            onSuccess = onSuccess,
            onFail = onFail
        )
    }

    fun register(
        onSuccess: () -> Unit,
        onFail: () -> Unit,
    ) {
        AuthenticationRepository.register(
            email = authenticationState.value.email,
            password = authenticationState.value.password,
            onSuccess = onSuccess,
            onFail = onFail
        )
    }


    fun googleSignIn(
        activityResultLauncher: ActivityResultLauncher<Intent>,
        context: Context
    ) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent = googleSignInClient.signInIntent
        activityResultLauncher.launch(signInIntent)
    }

    fun handleGoogleSignInResult(data: Intent?, onSuccess: () -> Unit, onFail: () -> Unit) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            onSuccess()
        } catch (e: ApiException) {
            onFail()
        }
    }
}