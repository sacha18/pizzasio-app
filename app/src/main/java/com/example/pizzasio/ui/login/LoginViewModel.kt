package com.example.pizzasio.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Patterns
import com.example.pizzasio.data.LoginRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pizzasio.R

class LoginViewModel(application: Application, private val
loginRepository: LoginRepository) : AndroidViewModel(application){

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // Créer une file de requêtes Volley
        val queue = Volley.newRequestQueue(getApplication())
        // Construire l'URL de votre API avec les paramètres requis (remplacez
        // URL_API par l'URL réelle)
        val apiUrl = "https://slam.cipecma.net/jsabbah/Api/Login?email=$username&password=$password"
        // Créer une requête JSON avec Volley
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, apiUrl, null,
            { response ->
                // Analyser la réponse JSON
                val userId = response.optString("id_user")
                if (!userId.isNullOrEmpty()) {
                    // La connexion réussit, créer l'objet LoggedInUserView
                    val user = LoggedInUserView(displayName = "User $userId")
                    _loginResult.value = LoginResult(success = user)
                } else {
                    // La réponse JSON ne contient pas "id_user", considérer
                    // comme échec
                            _loginResult.value = LoginResult(error =
                    R.string.login_failed)
                }
            },
            {
                // En cas d'erreur, considérer comme échec
                _loginResult.value = LoginResult(error = R.string.login_no)
            }
        )
        // Ajouter la requête à la file de requêtes
        queue.add(jsonObjectRequest)
    }


    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 3
    }
}