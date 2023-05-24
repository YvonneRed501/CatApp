package com.example.catapp.ui.ReusableComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.catapp.R
import com.example.catapp.ui.screens.CatAppScreen
import kotlinx.coroutines.delay


@Composable
fun CatAppBar(
    currentScreen: CatAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)

                    )
                }
            }
        }
    )
}

@Composable
fun Snackbar(
    text: String,
    dismissSnackbar: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        androidx.compose.material.Snackbar() {
            Text(text = text,
                textAlign = TextAlign.Center
            )
        }
    }
    LaunchedEffect(Unit) {
        delay(5000)
        dismissSnackbar()

    }
}