package com.example.catapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.catapp.ui.screens.*
import com.example.catapp.ui.theme.CatAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatAppTheme {
                val registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.factory)
                val viewModel: CatViewModel =
                    viewModel(factory = CatViewModel.Factory)
                val signInViewModel: SignInViewModel = viewModel(factory = SignInViewModel.factory)
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    signInViewModel.signInState.collect {
                        when (it) {
                            is SignInViewModel.SignInState.SignedOut -> navController.popBackStack(
                                CatAppScreen.Start.name,
                                inclusive = false
                            )
                            is SignInViewModel.SignInState.SignedIn -> {
                                println(navController)
                                navController.navigate(CatAppScreen.Home.name)
                            }
                        }
                    }
                }

                CatApp(viewModel, navController, signInViewModel, registerViewModel)
            }
        }
    }
}


