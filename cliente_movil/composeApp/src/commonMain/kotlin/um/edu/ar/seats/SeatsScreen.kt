package um.edu.ar.seats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import um.edu.ar.model.AsientoSeleccionado

@Composable
fun SeatsScreen(
    eventoId: Long,
    sessionId: String,
    rows: Int,
    cols: Int,
    vm: SeatsViewModel,
    onBlockedOk: (selected: List<AsientoSeleccionado>) -> Unit,
    onBack: () -> Unit
) {
    val state by vm.state.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(eventoId, sessionId) {
        vm.load(eventoId, sessionId)
    }

    Column(Modifier.fillMaxSize().padding(12.dp)) {

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }

        Spacer(Modifier.height(8.dp))

        // ✅ Leyenda simple
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Legend("LIBRE", colorFor("LIBRE"))
            Legend("SEL", colorFor("SELECCIONADO"))
            Legend("BLOQ", colorFor("BLOQUEADO"))
            Legend("VEND", colorFor("VENDIDO"))
        }

        Spacer(Modifier.height(8.dp))

        state.error?.let {
            Text(it)
            Spacer(Modifier.height(8.dp))
        }

        if (state.isLoading) {
            Text("Cargando asientos...")
            return@Column
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(cols),
            modifier = Modifier.weight(1f)
        ) {
            val all = (1..rows).flatMap { r -> (1..cols).map { c -> r to c } }

            items(all) { (r, c) ->
                val key = r to c
                val backendEstado = (state.seats[key] ?: "LIBRE").trim().uppercase()
                val selected = state.selected.containsKey(key)

                val effective = if (selected) "SELECCIONADO" else backendEstado
                val enabled = effective == "LIBRE" || effective == "SELECCIONADO"

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(34.dp)
                        .background(colorFor(effective))
                        .clickable(enabled = enabled) { vm.toggleSeat(r, c) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$r,$c",
                        fontSize = 9.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                scope.launch {
                    val ok = vm.bloquearSeleccionados(eventoId, sessionId)
                    val selectedSeats = state.selected.values.toList()
                    if (ok) {
                        onBlockedOk(selectedSeats)
                    } else {
                        vm.load(eventoId, sessionId)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.selected.isNotEmpty()
        ) {
            Text("Continuar y bloquear (${state.selected.size}/4)")
        }
    }
}

@Composable
private fun Legend(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(12.dp).background(color))
        Spacer(Modifier.width(6.dp))
        Text(label)
    }
}
private fun colorFor(estado: String): Color = when (estado.trim().uppercase()) {
    "LIBRE" -> Color(0xFFB9F6CA.toInt())
    "SELECCIONADO" -> Color(0xFF82B1FF.toInt())
    "BLOQUEADO" -> Color(0xFFFFF59D.toInt())
    "VENDIDO" -> Color(0xFFFF8A80.toInt())
    else -> Color.LightGray
}


