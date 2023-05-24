package com.example.catapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catapp.R
import com.example.catapp.ui.screens.RegisterViewModel.CredentialsValidState.Error.*
import com.example.catapp.ui.screens.RegisterViewModel.CredentialsValidState.*
import kotlinx.coroutines.delay
import org.w3c.dom.Text
import com.example.catapp.ui.ReusableComponents.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel,
    onBackButtonClicked: () -> Unit,
    onRegisterComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmedPassword by remember { mutableStateOf("")}
    val credentialsValidState by registerViewModel.credentialsValidState.collectAsState()
    val passwordErrorState = listOf(PasswordDoesNotMatch, PasswordDoesNotMeetRequirements )
    val keyboardController = LocalSoftwareKeyboardController.current
    val hideKeyboard = {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    when (credentialsValidState) {
        CredentialsValid -> {
            onRegisterComplete()
            registerViewModel.resetState()
        }
        UsernameBlank -> {
            Snackbar(text = stringResource(id = R.string.username_blank),
                dismissSnackbar = {registerViewModel.resetState()}
            )
           
        }
        PasswordDoesNotMeetRequirements -> {
            Snackbar(text = stringResource(id = R.string.Password_does_not_meet_requirements),
            dismissSnackbar = {registerViewModel.resetState()})

        }
        PasswordDoesNotMatch -> {
            Snackbar(text = stringResource(id = R.string.Password_does_not_match),
            dismissSnackbar = {registerViewModel.resetState()})
        }
    }
   

    Column(modifier = Modifier) {

        Text(text = "Please Enter a Username and Password",

                fontSize = 20.sp,
        textAlign = TextAlign.Justify )

        Spacer(Modifier.height(24.dp))

        EditTextBox(label = R.string.username,
            keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
             ),
            keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
             ),
            value = username,
            isError = credentialsValidState == UsernameBlank,
            onValueChanged = {username = it})

        Spacer(Modifier.height(24.dp))

        EditTextBox(label =R.string.password,
            keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ), keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }

        ),
            isError = credentialsValidState in passwordErrorState,
            value = password, onValueChanged = {password = it}
        
        )

        Spacer(Modifier.height(24.dp))

        EditTextBox(
            label =R.string.confirm_password,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { hideKeyboard() }
            ),
            isError = credentialsValidState in passwordErrorState,
            value = confirmedPassword,
            onValueChanged = {confirmedPassword = it})

        Spacer(Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Button( modifier = modifier.weight(1f),
                onClick = onBackButtonClicked) {
                Text(text = "Back")
              

            }
            Button(modifier = modifier
                .weight(1f)
                .padding(start = 16.dp),
                onClick =
            { registerViewModel.checkCredentialsValid(username, password, confirmedPassword)
                hideKeyboard()
            }
            ) {
                Text(text = "Register")

            }
        }
// need to work out where to put the snack bar in and get it to pick up on the credentials and error state
    }
}
@Composable
fun EditTextBox(
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
        onValueChange = onValueChanged,
        isError = isError,
        label = {Text(text =  stringResource(label))
        },
        keyboardOptions = keyboardOptions, keyboardActions = keyboardActions

    )
}




