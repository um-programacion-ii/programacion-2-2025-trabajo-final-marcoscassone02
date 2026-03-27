package um.edu.ar.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventoDetalleResponse(
    val titulo: String,
    val resumen: String,
    val descripcion: String,
    val fecha: String,
    val direccion: String,
    val imagen: String? = null,
    @SerialName("filaAsientos")
    val filaAsientos: Int,
    @SerialName("columnAsientos")
    val columnAsientos: Int,
    @SerialName("precioEntrada")
    val precioEntrada: Double,
    val eventoTipo: EventoTipoDto,
    val integrantes: List<IntegranteDto>,
    val id: Long
) {
    @Serializable
    data class EventoTipoDto(
        val nombre: String,
        val descripcion: String
    )

    @Serializable
    data class IntegranteDto(
        val nombre: String,
        val apellido: String,
        val identificacion: String
    )
}
