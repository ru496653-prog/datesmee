package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.AuthRepository
import com.example.data.repository.AuthUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Authenticated(val user: AuthUser) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    val currentUserState: StateFlow<AuthUser?> = authRepository.currentUserState

    private val _uiState = MutableStateFlow<AuthUiState>(
        authRepository.currentUserState.value?.let { AuthUiState.Authenticated(it) } ?: AuthUiState.Idle
    )
    val uiState: StateFlow<AuthUiState> = _uiState

    fun loginEmail(email: String, pass: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val res = authRepository.loginWithEmail(email, pass)
            res.fold(
                onSuccess = { user -> _uiState.value = AuthUiState.Authenticated(user) },
                onFailure = { err -> _uiState.value = AuthUiState.Error(err.message ?: "Login failed") }
            )
        }
    }

    fun registerEmail(email: String, pass: String, name: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val res = authRepository.registerWithEmail(email, pass, name)
            res.fold(
                onSuccess = { user -> _uiState.value = AuthUiState.Authenticated(user) },
                onFailure = { err -> _uiState.value = AuthUiState.Error(err.message ?: "Registration failed") }
            )
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val res = authRepository.signInWithGoogleToken(idToken)
            res.fold(
                onSuccess = { user -> _uiState.value = AuthUiState.Authenticated(user) },
                onFailure = { err -> _uiState.value = AuthUiState.Error(err.message ?: "Google Sign-In failed") }
            )
        }
    }

    fun loginGuest() {
        val user = authRepository.loginAsGuest()
        _uiState.value = AuthUiState.Authenticated(user)
    }

    fun sendForgotPassword(email: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            authRepository.sendPasswordReset(email)
            onComplete(true)
        }
    }

    fun logout() {
        authRepository.logout()
        _uiState.value = AuthUiState.Idle
    }
}
