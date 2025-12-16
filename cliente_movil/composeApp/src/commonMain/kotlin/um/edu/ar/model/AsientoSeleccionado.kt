package um.edu.ar.model

import kotlinx.serialization.Serializable

@Serializable
data class AsientoSeleccionado(
    val fila: Int,
    val columna: Int,
    val persona: String = ""
)