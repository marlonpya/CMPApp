package org.example.project.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import composemultiplatformcourse.composeapp.generated.resources.Login
import composemultiplatformcourse.composeapp.generated.resources.Res
import composemultiplatformcourse.composeapp.generated.resources.password
import composemultiplatformcourse.composeapp.generated.resources.success
import composemultiplatformcourse.composeapp.generated.resources.user
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource

@Serializable
object Login

@Composable
fun LoginScreen(onLoggedIn: ()-> Unit,
                viewModel: LoginViewModel = viewModel { LoginViewModel() }) {

    LaunchedEffect(viewModel.state.loggedIn) {
        if (viewModel.state.loggedIn) onLoggedIn()
    }
    val state = viewModel.state
    val message = when {
        state.loggedIn -> stringResource(Res.string.success)
        state.error != null -> stringResource(state.error)
        else -> null
    }
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val buttonState by remember {
        derivedStateOf {
            user.trim().isNotBlank() && password.trim().isNotEmpty()
        }
    }
    val messageError by remember { derivedStateOf { state.error != null } }

    var isPassVisible by remember { mutableStateOf(false) }
    val visualState by remember { derivedStateOf { if (isPassVisible) VisualTransformation.None else PasswordVisualTransformation() } }

    val iconVectorState by remember { derivedStateOf { if (isPassVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility } }
    val iconVectorContentState by remember { derivedStateOf { if (isPassVisible) "Hide password" else "Show password" } }

    Scaffold() { paddinValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddinValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text(stringResource(Res.string.user)) },
                isError = messageError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                onDone = { if (buttonState) viewModel.login(user, password) },
                isError = state.error != null
            )
            AnimatedVisibility(buttonState) {
                Button(
                    onClick = {
                        viewModel.login(user, password)
                    }
                ) {
                    Text(stringResource(Res.string.Login))
                }
            }
            if (message != null) {
                Text(message)
            }
        }
    }
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    var isPassVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(Res.string.password)) },
        isError = isError,
        visualTransformation = if (isPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { isPassVisible = !isPassVisible }) {
                Icon(
                    imageVector = if (isPassVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (isPassVisible) "Hide password" else "Show password"
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions { onDone() }
    )
}