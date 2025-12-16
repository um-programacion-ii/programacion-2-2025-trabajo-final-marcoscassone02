package um.edu.ar.model

import kotlinx.serialization.Serializable

@Serializable
data class AsientoPosicion(
    val fila: Int,
    val columna: Int
)