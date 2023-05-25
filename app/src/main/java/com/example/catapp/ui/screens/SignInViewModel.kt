package com.example.catapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.catapp.DogApplication
import com.example.catapp.domain.CredentialsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val credentialsRepository: CredentialsRepository) : ViewModel() {
    sealed class SignInState {
        object SignedIn : SignInState()
        object SignedOut : SignInState()
        object AppFirstLaunched : SignInState()
        sealed class Error : SignInState() {
            object UsernameInvalid : Error()
            object PasswordInvalid : Error()
        }
    }

    private val _signInState: MutableStateFlow<SignInState> =
        MutableStateFlow(SignInState.AppFirstLaunched)
    val signInState = _signInState.asStateFlow()
    private val _usernameInput: MutableStateFlow<String> = MutableStateFlow("")
    val usernameInput = _usernameInput.asStateFlow()
    private val _passwordInput: MutableStateFlow<String> = MutableStateFlow("")
    val passwordInput: StateFlow<String> = _passwordInput.asStateFlow()


    //check if credentials are valid, if yes sign in

    fun checkCredentialsEntered() {
        viewModelScope.launch {
            println(_signInState.value)
            println(usernameInput.value)
            println(passwordInput.value)

            val usernameExists = credentialsRepository.checkUsernameExists(usernameInput.value)
            val validCredentials =
                credentialsRepository.checkCredentials(usernameInput.value, passwordInput.value)

            when {
                !usernameExists -> _signInState.value = SignInState.Error.UsernameInvalid
                !validCredentials -> _signInState.value = SignInState.Error.PasswordInvalid
                else -> _signInState.value = SignInState.SignedIn
            }
        }
        println(_signInState.value)
    }

    fun resetState() {
        _signInState.value = SignInState.AppFirstLaunched
    }

    fun setUsername(username: String) {
        _usernameInput.value = username
    }

    fun setPassword(password: String) {
        _passwordInput.value = password
    }

    fun signOut() {
        _signInState.value = SignInState.SignedOut
    }
    //private val _signInSucessful: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    //  val signInSucessful = _signInSucessful.asStateFlow()

    // fun resetSignIn() {
    //     _signInSucessful.value = null
    // }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DogApplication)
                val credentialsRepository = application.container.credentialsRepository
                SignInViewModel(credentialsRepository = credentialsRepository)
            }
        }
    }
}