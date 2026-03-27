package um.edu.ar.sells

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import um.edu.ar.model.AsientoSeleccionado

@Composable
fun CheckoutScreen(
    eventoId: Long,
    sessionId: String,
    seats: List<AsientoSeleccionado>,
    precioEvento: Double?,
    vm: CheckoutViewModel,
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val state by vm.state.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(seats) {
        vm.init(seats)
    }

    val venta = state.ventaExitosa
    if (venta != null) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                Button(onClick = onSuccess) {
                    Text("Aceptar")
                }
            },
            title = { Text("Venta realizada con éxito") },
            text = {
                Column {
                    Text("Evento: ${venta.eventoId}")
                    Spacer(Modifier.height(8.dp))
                    Text("Precio total: $${venta.precioTotal}")
                    Spacer(Modifier.height(8.dp))
                    venta.asientos.forEach {
                        Text("Fila ${it.fila}, Col ${it.columna} → ${it.persona}")
                    }
                }
            }
        )
        return
    }

    Column(Modifier.fillMaxSize().padding(12.dp)) {

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }

        Spacer(Modifier.height(8.dp))

        Text("Evento: $eventoId")

        precioEvento?.let {
            Spacer(Modifier.height(8.dp))
            Text("Precio por entrada: $ $it")
        }

        Spacer(Modifier.height(12.dp))

        state.seats.forEachIndexed { index, asiento ->
            Text("Asiento (${asiento.fila}, ${asiento.columna})")

            TextField(
                value = asiento.persona,
                onValueChange = { vm.onPersonaChange(index, it) },
                label = { Text("Persona") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            )

            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                scope.launch {
                    vm.confirmarCompra(eventoId, sessionId)
                }
            },
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.isLoading) "Procesando..." else "Confirmar compra")
        }

        state.error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}



