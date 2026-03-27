package um.edu.ar.model

import kotlinx.serialization.Serializable

@Serializable
data class RealizarVentaResponse(
    val resultado: Boolean,
    val descripcion: String,
    val eventoId: Long,
    val ventaId: Long? = null,
    val precioVenta: Double = 0.0,
    val asientos: List<AsientoEstadoDto> = emptyList()
) {
    @Serializable
    data class AsientoEstadoDto(
        val fila: Int,
        val columna: Int,
        val persona: String,
        val estado: String
    )
}
