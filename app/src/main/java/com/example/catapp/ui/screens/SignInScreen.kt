package com.example.catapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catapp.R
import com.example.catapp.ui.ReusableComponents.Snackbar
import com.example.catapp.ui.screens.SignInViewModel.SignInState.Error.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    onRegisterButtonClicked: () -> Unit,
    signInViewModel: SignInViewModel,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val username by signInViewModel.usernameInput.collectAsState()
    val password by signInViewModel.passwordInput.collectAsState()
    val credentialsValidState by signInViewModel.signInState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val hideKeyboard = {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    when(credentialsValidState) {
        UsernameInvalid -> {
        Snackbar(text = stringResource(id = R.string.username_blank),
          dismissSnackbar = {signInViewModel.resetState()}  )
        }
        PasswordInvalid -> {
            Snackbar(text = stringResource(id = R.string.password_incorrect),
            dismissSnackbar = {signInViewModel.resetState()})
    }

    }


    Column(

        modifier = Modifier
            .fillMaxHeight()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

        ) {
        Text(
            text = stringResource(R.string.welcome),
            fontSize = 36.sp
        )
        Spacer(Modifier.height(4.dp))
        EditTextField(
            label = R.string.username,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = credentialsValidState == UsernameInvalid,
            value = username,
            onValueChanged = { signInViewModel.setUsername(it) }
        )
        Spacer(Modifier.height(12.dp))
        EditTextField(
            label = R.string.password,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {hideKeyboard() }
            ),
            isError = credentialsValidState == PasswordInvalid,
            value = password,
            onValueChanged = { signInViewModel.setPassword(it) }
        )

        Spacer(Modifier.height(64.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                modifier = modifier.weight(1f),
                onClick = onRegisterButtonClicked
            ) {
                Text(text = stringResource(R.string.register))
            }
            Button(
                modifier = modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                onClick = { signInViewModel.checkCredentialsEntered() }) {
                Text(text = stringResource(R.string.sign_in))
            }
        }

    }
}

@Composable
fun EditTextField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    value: String,
    isError: Boolean = false,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier

) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        onValueChange = {
                        onValueChanged(it)
                        },
        isError = isError,
            label = {
                Text(text =  stringResource(label))
            },
            keyboardOptions = keyboardOptions, keyboardActions = keyboardActions

            )
        }
