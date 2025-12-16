package um.edu.ar.sells

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import um.edu.ar.data.BackendApi
import um.edu.ar.model.AsientoSeleccionado
import um.edu.ar.model.VentaResultadoUi

class CheckoutViewModel(
    private val api: BackendApi
) {
    private val _state = MutableStateFlow(CheckoutUiState())
    val state: StateFlow<CheckoutUiState> = _state

    fun init(seats: List<AsientoSeleccionado>) {
        _state.update { it.copy(seats = seats) }
    }

    fun onPersonaChange(index: Int, persona: String) {
        _state.update { s ->
            val list = s.seats.toMutableList()
            list[index] = list[index].copy(persona = persona)
            s.copy(seats = list)
        }
    }
    suspend fun confirmarCompra(eventoId: Long, sessionId: String): Boolean {
        val seats = _state.value.seats

        if (seats.any { it.persona.isBlank() }) {
            _state.update { it.copy(error = "Completá el nombre de todos los asientos") }
            return false
        }

        _state.update { it.copy(isLoading = true, error = null) }

        return try {
            val resp = api.realizarVenta(eventoId, sessionId, seats)

            if (!resp.resultado) {
                _state.update { it.copy(isLoading = false, error = resp.descripcion) }
                false
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        ventaExitosa = VentaResultadoUi(
                            eventoId = resp.eventoId,
                            ventaId = resp.ventaId,
                            precioTotal = resp.precioVenta,
                            asientos = seats
                        ),
                        error = null
                    )
                }
                true
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = e.message ?: "Error realizando la venta"
                )
            }
            false
        }
    }
}




