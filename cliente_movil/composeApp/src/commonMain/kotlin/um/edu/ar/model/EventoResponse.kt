package um.edu.ar.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventoResponse(
    val id: Long,
    val nombre: String,
    val resumen: String,
    val descripcion: String,
    @SerialName("fechaHora")
    val fechaHora: String,
    val direccion: String,
    val tipoNombre: String,
    val filas: Int,
    val columnas: Int,
    @SerialName("precio")
    val precio: Double
)
