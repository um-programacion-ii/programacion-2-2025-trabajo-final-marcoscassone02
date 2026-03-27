package um.edu.ar.sells

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import um.edu.ar.model.VentaResumenResponse

@Composable
fun VentasScreen(
    sessionId: String,
    vm: VentasViewModel,
    onVentaClick: (Long) -> Unit
)  {
    val state by vm.state.collectAsState()

    LaunchedEffect(sessionId) {
        vm.load(sessionId)
    }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Text("Ventas", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        if (state.isLoading) {
            Text("Cargando...")
            return@Column
        }

        if (state.ventas.isEmpty()) {
            Text("Aún no hay ventas registradas.")
            return@Column
        }

        LazyColumn {
            items(state.ventas) { v ->
                VentaRow(v) {
                    val id = v.ventaId
                    if (id != null) onVentaClick(id)
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun VentaRow(v: VentaResumenResponse, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().clickable { onClick() }) {
        Column(Modifier.padding(12.dp)) {
            Text("Venta ID: ${v.ventaId ?: "-"}")
            Text("Evento: ${v.eventoId}")
            Text("Resultado: ${if (v.resultado) "OK" else "FALLÓ"}")
            Text("Asientos: ${v.cantidadAsientos}")
            Text("Precio: $${v.precioVenta}")
            Text(v.descripcion, style = MaterialTheme.typography.bodySmall)
        }
    }
}
