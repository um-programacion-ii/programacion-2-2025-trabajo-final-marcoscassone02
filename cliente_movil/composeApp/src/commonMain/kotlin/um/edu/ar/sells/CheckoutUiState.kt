package um.edu.ar.sells

import um.edu.ar.model.AsientoSeleccionado
import um.edu.ar.model.VentaResultadoUi

data class CheckoutUiState(
    val isLoading: Boolean = false,
    val seats: List<AsientoSeleccionado> = emptyList(),
    val error: String? = null,
    val ventaExitosa: VentaResultadoUi? = null
)

