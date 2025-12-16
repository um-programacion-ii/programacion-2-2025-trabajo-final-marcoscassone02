package um.edu.ar.sells

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import um.edu.ar.data.BackendApi
import um.edu.ar.model.VentaResumenResponse

data class VentasUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val ventas: List<VentaResumenResponse> = emptyList()
)

class VentasViewModel(
    private val api: BackendApi
) {
    private val _state = MutableStateFlow(VentasUiState())
    val state: StateFlow<VentasUiState> = _state

    suspend fun load(sessionId: String) {
        _state.update { it.copy(isLoading = true, error = null) }

        try {
            val list = api.getVentas(sessionId)
                .filter { it.resultado }

            _state.update {
                it.copy(
                    isLoading = false,
                    ventas = list
                )
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = e.message ?: "Error cargando ventas"
                )
            }
        }
    }
}

