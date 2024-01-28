package zec.puka.pumpapp.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


object AuthenticationRepository {
    private val auth by lazy {
        Firebase.auth
    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit,
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    onFail()
                    //onSuccess()
                    Log.d("AUTH", "User not logged in")
                }
            }
    }
    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    onFail()
                    //onSuccess()
                    Log.d("AUTH", "User not registered")
                }
            }
    }
}