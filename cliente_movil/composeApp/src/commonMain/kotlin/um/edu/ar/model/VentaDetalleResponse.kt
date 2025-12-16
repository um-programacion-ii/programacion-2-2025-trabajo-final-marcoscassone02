package um.edu.ar.model

import kotlinx.serialization.Serializable

@Serializable
data class VentaDetalleResponse(
    val eventoId: Long,
    val ventaId: Long? = null,
    val fechaVenta: String,
    val asientos: List<AsientoVentaResponse> = emptyList(),
    val resultado: Boolean,
    val descripcion: String,
    val precioVenta: Double
) {
    @Serializable
    data class AsientoVentaResponse(
        val fila: Int,
        val columna: Int,
        val persona: String,
        val estado: String
    )
}
