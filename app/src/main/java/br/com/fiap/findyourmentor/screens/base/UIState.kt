package br.com.fiap.findyourmentor.screens.base

import br.com.fiap.findyourmentor.model.User

sealed class UIState {
    data object Idle: UIState()
    data object Loading: UIState()
    data class Error(val error: String): UIState()
    data class Success(val data: MutableList<User>): UIState()
}