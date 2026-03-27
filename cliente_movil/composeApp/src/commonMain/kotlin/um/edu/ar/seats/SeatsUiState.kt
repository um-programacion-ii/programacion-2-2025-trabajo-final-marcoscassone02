package um.edu.ar.seats

import um.edu.ar.model.AsientoSeleccionado

data class SeatsUiState(
    val seats: Map<Pair<Int,Int>, String> = emptyMap(),
    val selected: Map<Pair<Int,Int>, AsientoSeleccionado> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)
