package br.com.fiap.findyourmentor.screens

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.findyourmentor.data.HomeRepository
import br.com.fiap.findyourmentor.screens.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): ViewModel(), DefaultLifecycleObserver {

    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun getRemoteUsers() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.value = UIState.Loading

            repository.getUsersList()
                .flowOn(Dispatchers.IO)
                .catch { ex ->
                    _uiState.value = UIState.Error(ex.toString())
                }
                .collect { data ->
                    _uiState.value = UIState.Success(data.toMutableList())
                }
        }
    }

    fun clearData() {
        viewModelScope.launch(Dispatchers.Main){
            _uiState.value = UIState.Success(mutableListOf())
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("CountriesViewModel", "CLEARED JOB")
    }
}