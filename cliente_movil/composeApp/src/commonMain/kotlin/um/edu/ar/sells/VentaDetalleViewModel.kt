package um.edu.ar.sells

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import um.edu.ar.data.BackendApi
import um.edu.ar.model.VentaDetalleResponse

data class VentaDetalleUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val detalle: VentaDetalleResponse? = null
)

class VentaDetalleViewModel(
    private val api: BackendApi
) {
    private val _state = MutableStateFlow(VentaDetalleUiState())
    val state: StateFlow<VentaDetalleUiState> = _state

    suspend fun load(ventaId: Long, sessionId: String) {
        _state.update { it.copy(isLoading = true, error = null) }
        try {
            val det = api.getVentaDetalle(ventaId, sessionId)
            _state.update { it.copy(isLoading = false, detalle = det) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message ?: "Error cargando detalle") }
        }
    }
}