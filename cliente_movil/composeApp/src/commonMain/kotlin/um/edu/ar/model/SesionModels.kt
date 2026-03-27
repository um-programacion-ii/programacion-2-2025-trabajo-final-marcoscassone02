package um.edu.ar.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SesionIniciarRequest(
    val username: String,
    val password: String
)

@Serializable
data class SesionIniciarResponse(
    val descripcion: String,
    val resultado: Boolean,
    val sessionId: String
)

@Serializable
data class SessionStateResponse(
    val sessionId: String,
    val username: String? = null,
    val pasoActual: String? = null,
    val eventoId: Long? = null,
    val ventaId: Long? = null,
    val asientos: List<AsientoSeleccionado>? = null
)
