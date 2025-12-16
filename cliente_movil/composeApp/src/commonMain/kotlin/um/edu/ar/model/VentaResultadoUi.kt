package um.edu.ar.model

data class VentaResultadoUi(
    val eventoId: Long,
    val ventaId: Long?,
    val precioTotal: Double,
    val asientos: List<AsientoSeleccionado>
)
