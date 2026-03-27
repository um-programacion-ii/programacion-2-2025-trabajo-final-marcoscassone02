package um.edu.ar.seats

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import um.edu.ar.data.BackendApi
import um.edu.ar.model.AsientoPosicion
import um.edu.ar.model.AsientoSeleccionado

class SeatsViewModel(
    private val api: BackendApi
) {
    private val _state = MutableStateFlow(SeatsUiState())
    val state: StateFlow<SeatsUiState> = _state

    suspend fun load(eventoId: Long, sessionId: String) {
        _state.update { it.copy(isLoading = true, error = null, selected = emptyMap()) }

        val asientos = api.getAsientos(eventoId, sessionId)
        val map = asientos.associate { (it.fila to it.columna) to it.estado.trim().uppercase() }

        _state.update { it.copy(isLoading = false, seats = map) }
    }

    fun toggleSeat(fila: Int, columna: Int) {
        val key = fila to columna
        val current = _state.value
        val estado = (current.seats[key] ?: "LIBRE").trim().uppercase()

        if (estado == "VENDIDO" || estado == "BLOQUEADO") return

        val selected = current.selected.toMutableMap()

        if (selected.containsKey(key)) {
            selected.remove(key)
        } else {
            if (selected.size >= 4) {
                _state.update { it.copy(error = "Podés seleccionar como máximo 4 asientos") }
                return
            }
            selected[key] = AsientoSeleccionado(fila = fila, columna = columna) // persona vacío
        }

        _state.update { it.copy(selected = selected, error = null) }
    }

    fun setPersona(fila: Int, columna: Int, persona: String) {
        val key = fila to columna
        _state.update { s ->
            val selected = s.selected.toMutableMap()
            val cur = selected[key] ?: return@update s
            selected[key] = cur.copy(persona = persona)
            s.copy(selected = selected)
        }
    }

    suspend fun bloquearSeleccionados(eventoId: Long, sessionId: String): Boolean {
        val current = _state.value
        if (current.selected.isEmpty()) {
            _state.update { it.copy(error = "Seleccioná al menos 1 asiento") }
            return false
        }

        val payload = current.selected.values
            .map { AsientoPosicion(it.fila, it.columna) }

        val ok = api.bloquearAsientos(eventoId, sessionId, payload)

        if (!ok) {
            _state.update { it.copy(error = "No se pudo bloquear (se agotaron o se bloquearon antes).") }
        }
        return ok
    }
}


