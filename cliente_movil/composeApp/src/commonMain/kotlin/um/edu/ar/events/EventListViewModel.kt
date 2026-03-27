package um.edu.ar.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import um.edu.ar.data.BackendApi
import um.edu.ar.model.EventoResponse


data class EventListUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val eventos: List<EventoResponse> = emptyList()
)

class EventListViewModel(
    private val backendApi: BackendApi,
    private val sessionId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventListUiState(isLoading = true))
    val uiState: StateFlow<EventListUiState> = _uiState

    init {
        cargarEventos()
    }

    private fun cargarEventos() {
        viewModelScope.launch {
            try {
                val eventos = backendApi.getEventos(sessionId)
                _uiState.value = EventListUiState(
                    isLoading = false,
                    eventos = eventos
                )
            } catch (e: Exception) {
                _uiState.value = EventListUiState(
                    isLoading = false,
                    error = e.message ?: "Error al cargar eventos"
                )
            }
        }
    }
}
