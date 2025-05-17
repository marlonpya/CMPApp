package org.example.project.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import composemultiplatformcourse.composeapp.generated.resources.Res
import composemultiplatformcourse.composeapp.generated.resources.invalid_password
import composemultiplatformcourse.composeapp.generated.resources.invalid_user
import org.jetbrains.compose.resources.StringResource

class LoginViewModel: ViewModel() {
    var state by mutableStateOf(UiState())
        private set

    data class UiState(
        val loggedIn: Boolean = false,
        val error: StringResource? = null
    )

    fun login(user: String, password: String) {
        state = when {
            !user.contains('@') -> UiState(error = Res.string.invalid_user)
            password.length < 5 -> UiState(error = Res.string.invalid_password)
            else -> UiState(loggedIn = true)
        }
    }
}