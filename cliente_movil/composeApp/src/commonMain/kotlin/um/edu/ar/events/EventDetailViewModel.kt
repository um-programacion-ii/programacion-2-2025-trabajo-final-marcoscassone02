package um.edu.ar.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import um.edu.ar.data.BackendApi
import um.edu.ar.model.EventoDetalleResponse

data class EventDetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val detalle: EventoDetalleResponse? = null
)

class EventDetailViewModel(
    private val backendApi: BackendApi,
    private val sessionId: String,
    private val eventoId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventDetailUiState(isLoading = true))
    val uiState: StateFlow<EventDetailUiState> = _uiState

    init { cargarDetalle() }

    private fun cargarDetalle() {
        viewModelScope.launch {
            try {
                val det = backendApi.getEventoDetalle(eventoId, sessionId)
                _uiState.value = EventDetailUiState(isLoading = false, detalle = det)
            } catch (e: Exception) {
                _uiState.value = EventDetailUiState(
                    isLoading = false,
                    error = e.message ?: "Error cargando detalle"
                )
            }
        }
    }
}

