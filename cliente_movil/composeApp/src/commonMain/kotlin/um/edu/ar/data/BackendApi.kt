package um.edu.ar.data

import um.edu.ar.model.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BackendApi(
    private val baseUrl: String
) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

    suspend fun iniciarSesion(
        username: String,
        password: String
    ): SesionIniciarResponse {
        return client.post("$baseUrl/api/sesion/iniciar") {
            contentType(ContentType.Application.Json)
            setBody(SesionIniciarRequest(username, password))
        }.body()
    }
    suspend fun getSessionState(sessionId: String): SessionStateResponse {
        return client.get("$baseUrl/api/sesion/estado") {
            header("X-Session-Id", sessionId)
        }.body()
    }

    suspend fun getEventos(sessionId: String): List<EventoResponse> {
        return client.get("$baseUrl/api/eventos") {
            header("X-Session-Id", sessionId)
        }.body()
    }

    suspend fun getEventoDetalle(
        id: Long,
        sessionId: String
    ): EventoDetalleResponse {
        return client.get("$baseUrl/api/eventos/$id/detalle") {
            header("X-Session-Id", sessionId)
        }.body()
    }

    suspend fun getAsientos(
        eventoId: Long,
        sessionId: String
    ): List<AsientoResponse> {
        return client.get("$baseUrl/api/eventos/$eventoId/asientos") {
            header("X-Session-Id", sessionId)
        }.body()
    }

    suspend fun bloquearAsientos(
        eventoId: Long,
        sessionId: String,
        asientos: List<AsientoPosicion>
    ): Boolean {
        val response = client.post("$baseUrl/api/eventos/$eventoId/bloquear-asientos") {
            header("X-Session-Id", sessionId)
            contentType(ContentType.Application.Json)
            setBody(
                BloquearAsientosRequest(
                    eventoId = eventoId,
                    asientos = asientos
                )
            )
        }

        return response.status.value in 200..299
    }

    suspend fun realizarVenta(
        eventoId: Long,
        sessionId: String,
        asientos: List<AsientoSeleccionado>
    ): RealizarVentaResponse {

        val req = RealizarVentaRequest(
            asientos = asientos.map {
                VentaAsientoDto(it.fila, it.columna, it.persona)
            }
        )

        val resp = client.post("$baseUrl/api/eventos/$eventoId/realizar-venta") {
            header("X-Session-Id", sessionId)
            contentType(ContentType.Application.Json)
            setBody(req)
        }

        if (!resp.status.isSuccess()) {
            throw IllegalStateException("HTTP ${resp.status.value}: ${resp.bodyAsText()}")
        }

        return resp.body()
    }


    suspend fun getVentas(sessionId: String): List<VentaResumenResponse> {
        return client.get("$baseUrl/api/ventas") {
            header("X-Session-Id", sessionId)
        }.body()
    }

    suspend fun getVentaDetalle(
        ventaId: Long,
        sessionId: String
    ): VentaDetalleResponse {
        return client.get("$baseUrl/api/ventas/$ventaId") {
            header("X-Session-Id", sessionId)
        }.body()
    }

    suspend fun cerrarSesion(sessionId: String) {
        val resp = client.post("$baseUrl/api/sesion/cerrar") {
            header("X-Session-Id", sessionId)
        }
        if (!resp.status.isSuccess()) {
            throw IllegalStateException("HTTP ${resp.status.value}: ${resp.bodyAsText()}")
        }
    }
}

