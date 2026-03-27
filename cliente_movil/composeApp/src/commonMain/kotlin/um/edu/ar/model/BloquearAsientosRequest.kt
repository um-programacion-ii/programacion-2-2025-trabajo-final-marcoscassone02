package um.edu.ar.model

import kotlinx.serialization.Serializable

@Serializable
data class BloquearAsientosRequest(
    val eventoId: Long,
    val asientos: List<AsientoPosicion>
)