package um.edu.ar.sells

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VentaDetalleScreen(
    ventaId: Long,
    sessionId: String,
    vm: VentaDetalleViewModel,
    onBack: () -> Unit
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(ventaId, sessionId) {
        vm.load(ventaId, sessionId)
    }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }

        Spacer(Modifier.height(12.dp))

        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        if (state.isLoading) {
            Text("Cargando detalle...")
            return@Column
        }

        val det = state.detalle ?: run {
            Text("No se encontró la venta.")
            return@Column
        }

        Text("Detalle de venta", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        Text("Venta ID: ${det.ventaId ?: "-"}")
        Text("Evento: ${det.eventoId}")
        Text("Fecha: ${det.fechaVenta}")
        Text("Resultado: ${if (det.resultado) "OK" else "FALLÓ"}")
        Text("Precio total: $${det.precioVenta}")
        Spacer(Modifier.height(8.dp))
        Text(det.descripcion)

        Spacer(Modifier.height(12.dp))

        Text("Asientos:", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(det.asientos) { a ->
                Text("• (${a.fila},${a.columna}) - ${a.persona} - ${a.estado}")
            }
        }
    }
}
