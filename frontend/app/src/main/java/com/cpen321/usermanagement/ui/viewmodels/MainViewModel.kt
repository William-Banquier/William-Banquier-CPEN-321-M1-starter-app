package com.cpen321.usermanagement.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // REQUIRED IMPORT
import com.cpen321.usermanagement.data.remote.dto.EventData
import com.cpen321.usermanagement.data.repository.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch // REQUIRED IMPORT for 'launch' itself
import javax.inject.Inject



data class MainUiState(
    val successMessage: String? = null,
    val isLoading: Boolean = false,
    val eventData: EventData? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(private val eventsRepository: EventsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun setSuccessMessage(message: String) {
        _uiState.value = _uiState.value.copy(successMessage = message)
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }


      fun fetchEvent() {
          Log.i("MainViewModel", "fetchEvent() called")
        viewModelScope.launch { // This launches a new coroutine in the ViewModel's scope
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null, eventData = null) // Also clear previous eventData
            try { // It's good practice to wrap repository calls in try-catch
                val result = eventsRepository.getEvents() // Assuming getEvents() is a suspend function
                result.fold(
                    onSuccess = { eventData ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            eventData = eventData
                        )
                    },
                    onFailure = { throwable ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "An unknown error occurred"
                        )
                    }
                )
            } catch (e: Exception) { // Catch any other exceptions during the repository call
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "An unexpected error occurred"
                )
            }
        }
    }
}
