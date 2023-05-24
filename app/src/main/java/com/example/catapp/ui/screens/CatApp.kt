package com.example.catapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.catapp.R
import com.example.catapp.data.CatRepository
import com.example.catapp.ui.ReusableComponents.CatAppBar
import kotlinx.coroutines.delay
import com.example.catapp.ui.screens.CatViewModel as CatViewModel

enum class CatAppScreen(@StringRes val title: Int) {
    Start(R.string.sign_in),
    Register(R.string.register),
    Home(R.string.app_name),
    Cat(R.string.default_value)
    // need to add titles but how do you make
// the title dynamic depending on cat selected
}

@Composable
fun CatApp(
    catViewModel: CatViewModel,
    navController: NavHostController,
    signInViewModel: SignInViewModel,
    registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.factory)
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CatAppScreen.valueOf(
        backStackEntry?.destination?.route ?: CatAppScreen.Start.name
    )
    val scaffoldState = rememberScaffoldState()



    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
        CatAppBar(currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = {
                if (currentScreen == CatAppScreen.Home) {
                    signInViewModel.signOut()
                } else {
                    navController.navigateUp()
                }
            }
        )
        }

    ) {
        NavHost(
            navController = navController,
            startDestination = CatAppScreen.Start.name

        ) {
            composable(route = CatAppScreen.Start.name) {
                SignInScreen(signInViewModel = signInViewModel,

                    onRegisterButtonClicked = {
                        navController.navigate(CatAppScreen.Register.name)
                    })
            }
            composable(route = CatAppScreen.Home.name) {
                CatHomeScreen(
                    dogsUiState = catViewModel.dogsUiState,
                    cats = CatRepository.cats,
                    catViewModel = catViewModel,
                    onCatSelected = {
                        navController.navigate(CatAppScreen.Cat.name)
                    }

                )
                BackHandler(true) {}
            }
            composable(route = CatAppScreen.Cat.name) {
                CatContentScreen(
                    catViewModel = catViewModel
                )

            }
            composable(route = CatAppScreen.Register.name) {
                RegisterScreen(
                    registerViewModel = registerViewModel,
                    onBackButtonClicked = {
                        navController.navigate(CatAppScreen.Start.name)
                    },
                onRegisterComplete = {navController.navigate(CatAppScreen.Start.name)}
                )
            }


        }

    }
}
