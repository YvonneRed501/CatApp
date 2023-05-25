package com.example.catapp

import com.example.catapp.domain.CredentialsRepository
import com.example.catapp.ui.screens.SignInViewModel
import org.junit.Test

class SignInViewModelTest {
    class TestCredentialsRepositoryImpl : CredentialsRepository {
        override suspend fun checkCredentials(username: String, password: String): Boolean {
            return username == "test" && password == "test"
        }

        override suspend fun checkUsernameExists(username: String): Boolean {
            return true
        }

        override suspend fun insertCredentials(username: String, password: String) {

        }

        override suspend fun updateCredentials(username: String, password: String) {
        }

        override suspend fun deleteCredentials(username: String, password: String) {
        }
    }

    private val credentialsRepo = TestCredentialsRepositoryImpl()
    private val signInViewModel = SignInViewModel(credentialsRepo)

    @Test
    fun `Calling resetState() set's signInState to AppFirstLaunched`() {
        signInViewModel.resetState()
        assert(signInViewModel.signInState.value == SignInViewModel.SignInState.AppFirstLaunched)
    }

    @Test
    fun `Calling signOut() set's SignInState to SignedOut` () {
        signInViewModel.signOut()
        assert(signInViewModel.signInState.value == SignInViewModel.SignInState.SignedOut)
    }
     @Test
     fun `Calling setUsername() sets username to  username input`() {
         signInViewModel.setUsername(username = "test")
         assert(signInViewModel.usernameInput.value == "test")
     }
    @Test
    fun `Calling setPassword() sets password to password input` () {
        signInViewModel.setPassword(password = "test")
        assert(signInViewModel.passwordInput.value == "test")
    }

 //  @Test
//   fun `Calling checkCredentialsEntered correctly returns true ` () = runBlocking{
//       signInViewModel.setUsername("test")
//       signInViewModel.setPassword("test")
//       signInViewModel.checkCredentialsEntered()
//       assert(signInViewModel.signInState.value == SignInViewModel.SignInState.SignedIn)
// }
    }

