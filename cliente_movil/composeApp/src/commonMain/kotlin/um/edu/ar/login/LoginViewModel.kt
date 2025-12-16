package um.edu.ar.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import um.edu.ar.data.BackendApi

class LoginViewModel(
    private val backendApi: BackendApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onUsernameChange(value: String) {
        _uiState.value = _uiState.value.copy(username = value)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }

    fun login() {
        val state = _uiState.value
        if (state.username.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = "Usuario y contraseña son obligatorios")
            return
        }

        _uiState.value = state.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val response = backendApi.iniciarSesion(state.username, state.password)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    sessionId = response.sessionId
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al iniciar sesión"
                )
            }
        }
    }
}

