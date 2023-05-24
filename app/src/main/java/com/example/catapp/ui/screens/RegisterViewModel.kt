package com.example.catapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.catapp.DogApplication
import com.example.catapp.data.Credentials
import com.example.catapp.domain.CredentialsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val credentialsRepository: CredentialsRepository) : ViewModel() {

    sealed class CredentialsValidState {
        object CredentialsValid : CredentialsValidState()
        object AppFirstLaunched : CredentialsValidState()

        //
        // set the error state if validation fails, then get the RegisterScreen
        // to pick up the error state and then pick up the corresponding string resource
        sealed class Error : CredentialsValidState() {
            object UsernameBlank : Error()
            object PasswordDoesNotMatch : Error()
            object PasswordDoesNotMeetRequirements : Error()

        }
    }


    private val _credentialsValidState: MutableStateFlow<CredentialsValidState> = MutableStateFlow(
        CredentialsValidState.AppFirstLaunched
    )


    val credentialsValidState = _credentialsValidState

    fun checkCredentialsValid(username: String, password: String, confirmedPassword: String) {
        if (username.isNotEmpty() && (password == confirmedPassword)) {
            createNewCredentials(username, password)
            _credentialsValidState.value = CredentialsValidState.CredentialsValid
            println(credentialsValidState.value)
        } else if (username.isEmpty()) {

            _credentialsValidState.value = CredentialsValidState.Error.UsernameBlank

        } else if (password.isNotEmpty() && ( password != confirmedPassword)) {

            _credentialsValidState.value = CredentialsValidState.Error.PasswordDoesNotMatch
        }

    }

    private fun createNewCredentials(username: String, password: String) {
        viewModelScope.launch {
            credentialsRepository.insertCredentials(
                    username = username,
                    password = password

            )
        }

    }
fun resetState(){
    _credentialsValidState.value = CredentialsValidState.AppFirstLaunched
}
    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DogApplication)
                val credentialsRepository = application.container.credentialsRepository
                RegisterViewModel(credentialsRepository = credentialsRepository)
            }
        }
    }
//
//    companion object {
//        fun CreationExtras.dogApplication(): DogApplication{
//            return  (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DogApplication)
//        }
//        val factory : ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                RegisterViewModel(dogApplication().container.credentialsRepository)
//            }
//        }
//    }
}


