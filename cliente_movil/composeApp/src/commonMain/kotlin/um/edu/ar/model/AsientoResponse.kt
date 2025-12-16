package um.edu.ar.model

import kotlinx.serialization.Serializable

@Serializable
data class AsientoResponse(
    val fila: Int,
    val columna: Int,
    val estado: String
)