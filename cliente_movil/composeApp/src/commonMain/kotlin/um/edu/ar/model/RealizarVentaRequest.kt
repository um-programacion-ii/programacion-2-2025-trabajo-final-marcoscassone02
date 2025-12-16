package um.edu.ar.model

import kotlinx.serialization.Serializable

@Serializable
data class RealizarVentaRequest(
    val asientos: List<VentaAsientoDto>
)

@Serializable
data class VentaAsientoDto(
    val fila: Int,
    val columna: Int,
    val persona: String
)

