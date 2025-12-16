package um.edu.ar.events

import coil3.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    viewModel: EventDetailViewModel,
    onElegirAsientos: (Long, Int, Int, Double) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del evento") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            // ✅ BOTÓN FIJO SIEMPRE VISIBLE
            if (state.detalle != null && !state.isLoading && state.error == null) {
                Button(
                    onClick = {
                        val e = state.detalle!!
                        onElegirAsientos(
                            e.id,
                            e.filaAsientos,
                            e.columnAsientos,
                            e.precioEntrada
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text("Elegir asientos")
                }
            }
        }
    ) { padding ->

        when {
            state.isLoading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                val evento = state.detalle!!

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    if (!evento.imagen.isNullOrBlank()) {
                        AsyncImage(
                            model = evento.imagen,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text(evento.titulo, style = MaterialTheme.typography.headlineSmall)
                    Text(evento.resumen)

                    Spacer(Modifier.height(8.dp))

                    Text("Fecha: ${evento.fecha}")
                    Text("Dirección: ${evento.direccion}")
                    Text("Precio: $ ${evento.precioEntrada}")

                    Spacer(Modifier.height(12.dp))

                    Text("Descripción", style = MaterialTheme.typography.titleMedium)
                    Text(evento.descripcion)

                    if (evento.integrantes.isNotEmpty()) {
                        Spacer(Modifier.height(12.dp))
                        Text("Integrantes", style = MaterialTheme.typography.titleMedium)
                        evento.integrantes.forEach {
                            Text("- ${it.nombre} ${it.apellido}")
                        }
                    }

                    Spacer(Modifier.height(72.dp))
                }
            }
        }
    }
}


