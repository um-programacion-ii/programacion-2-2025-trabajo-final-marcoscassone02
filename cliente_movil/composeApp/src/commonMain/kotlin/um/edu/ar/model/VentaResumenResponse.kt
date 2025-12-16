package um.edu.ar.model

import kotlinx.serialization.Serializable

@Serializable
data class VentaResumenResponse(
    val eventoId: Long,
    val ventaId: Long? = null,
    val fechaVenta: String,
    val resultado: Boolean,
    val descripcion: String,
    val precioVenta: Double,
    val cantidadAsientos: Int
)
