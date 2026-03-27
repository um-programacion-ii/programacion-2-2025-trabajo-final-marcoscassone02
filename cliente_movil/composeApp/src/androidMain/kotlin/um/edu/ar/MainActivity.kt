package um.edu.ar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import um.edu.ar.data.BASE_URL
import um.edu.ar.data.BackendApi
import um.edu.ar.data.SessionStore
import um.edu.ar.events.*
import um.edu.ar.login.*
import um.edu.ar.model.AsientoSeleccionado
import um.edu.ar.seats.SeatsScreen
import um.edu.ar.sells.*
import um.edu.ar.ui.AppSection
import um.edu.ar.ui.BottomNavBar
import um.edu.ar.user.UsuarioScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {

                val ctx = LocalContext.current
                val api = remember { BackendApi(BASE_URL) }
                val scope = rememberCoroutineScope()

                // ✅ Sesión en memoria (no auto-login)
                var sessionId by remember { mutableStateOf<String?>(null) }

                // UI state general
                var section by remember { mutableStateOf(AppSection.EVENTOS) }

                // Eventos flow
                var selectedEventoId by remember { mutableStateOf<Long?>(null) }
                var seatsEventoId by remember { mutableStateOf<Long?>(null) }
                var seatsRows by remember { mutableStateOf<Int?>(null) }
                var seatsCols by remember { mutableStateOf<Int?>(null) }

                // Checkout (carga de nombres)
                var checkoutEventoId by remember { mutableStateOf<Long?>(null) }
                var checkoutSeats by remember { mutableStateOf<List<AsientoSeleccionado>>(emptyList()) }
                var checkoutPrecio by remember { mutableStateOf<Double?>(null) }

                // Ventas flow
                var selectedVentaId by remember { mutableStateOf<Long?>(null) }

                fun clearUiState() {
                    selectedEventoId = null
                    seatsEventoId = null
                    seatsRows = null
                    seatsCols = null
                    checkoutEventoId = null
                    checkoutSeats = emptyList()
                    checkoutPrecio = null
                    selectedVentaId = null
                }

                suspend fun hydrateSeatDimensions(session: String, eventoId: Long) {
                    val det = api.getEventoDetalle(eventoId, session)
                    seatsRows = det.filaAsientos
                    seatsCols = det.columnAsientos
                    checkoutPrecio = det.precioEntrada
                }

                // ✅ Login siempre (no restauramos automáticamente al abrir)
                if (sessionId == null) {
                    val loginViewModel = remember { provideLoginViewModel() }

                    LoginScreen(
                        viewModel = loginViewModel,
                        onLoginSuccess = { sid ->
                            // Guardar sesión
                            SessionStore.save(ctx, sid)
                            sessionId = sid

                            // Restaurar estado SOLO después de login
                            scope.launch {
                                try {
                                    val st = api.getSessionState(sid)

                                    clearUiState()

                                    // Si estaba en asientos o checkout, necesitamos filas/cols para no romper el grid
                                    if ((st.pasoActual == "SELECCION_ASIENTOS" || st.pasoActual == "CARGA_NOMBRES")
                                        && st.eventoId != null
                                    ) {
                                        hydrateSeatDimensions(sid, st.eventoId)
                                    }

                                    restoreFromState(
                                        state = st,
                                        setSection = { section = it },
                                        setEvento = { selectedEventoId = it },
                                        setSeatsEvento = { seatsEventoId = it },
                                        setCheckoutEvento = { checkoutEventoId = it },
                                        setCheckoutSeats = { checkoutSeats = it },
                                        setVenta = { selectedVentaId = it }
                                    )

                                } catch (_: Exception) {
                                    section = AppSection.EVENTOS
                                    clearUiState()
                                }
                            }
                        }
                    )
                    return@MaterialTheme
                }

                Scaffold(
                    bottomBar = {
                        BottomNavBar(
                            current = section,
                            onSelect = { section = it }
                        )
                    }
                ) { padding ->

                    Box(Modifier.padding(padding)) {

                        when (section) {

                            /* ==================== EVENTOS ==================== */
                            AppSection.EVENTOS -> {

                                // Checkout (CARGA_NOMBRES)
                                if (checkoutEventoId != null) {
                                    val checkoutVm = remember(checkoutEventoId) {
                                        provideCheckoutViewModel()
                                    }

                                    CheckoutScreen(
                                        eventoId = checkoutEventoId!!,
                                        sessionId = sessionId!!,
                                        seats = checkoutSeats,
                                        precioEvento = checkoutPrecio,
                                        vm = checkoutVm,
                                        onSuccess = {
                                            // limpiar checkout
                                            checkoutEventoId = null
                                            checkoutSeats = emptyList()
                                            checkoutPrecio = null
                                            seatsEventoId = null
                                            seatsRows = null
                                            seatsCols = null

                                            // ir a ventas
                                            section = AppSection.VENTAS

                                            // si el backend guardó ventaId, abrimos detalle
                                            scope.launch {
                                                try {
                                                    val st = api.getSessionState(sessionId!!)
                                                    if (st.ventaId != null) {
                                                        selectedVentaId = st.ventaId
                                                    }
                                                } catch (_: Exception) {
                                                }
                                            }
                                        },
                                        onBack = {
                                            checkoutEventoId = null
                                        }
                                    )
                                    return@Box
                                }

                                // Selección de asientos
                                if (seatsEventoId != null) {
                                    val seatsVm = remember(seatsEventoId) {
                                        provideSeatsViewModel()
                                    }

                                    SeatsScreen(
                                        eventoId = seatsEventoId!!,
                                        sessionId = sessionId!!,
                                        rows = (seatsRows ?: 1).coerceAtLeast(1), // ✅ blindaje
                                        cols = (seatsCols ?: 1).coerceAtLeast(1), // ✅ blindaje
                                        vm = seatsVm,
                                        onBlockedOk = { seats ->
                                            // ir a checkout
                                            checkoutEventoId = seatsEventoId!!
                                            checkoutSeats = seats
                                            seatsEventoId = null
                                        },
                                        onBack = {
                                            seatsEventoId = null
                                        }
                                    )
                                    return@Box
                                }

                                // Lista de eventos
                                if (selectedEventoId == null) {
                                    val listVm = remember(sessionId) {
                                        provideEventListViewModel(sessionId!!)
                                    }

                                    EventListScreen(
                                        viewModel = listVm,
                                        onEventoClick = { selectedEventoId = it.id }
                                    )
                                    return@Box
                                }

                                // Detalle de evento
                                val detailVm = remember(selectedEventoId) {
                                    provideEventDetailViewModel(sessionId!!, selectedEventoId!!)
                                }

                                EventDetailScreen(
                                    viewModel = detailVm,
                                    onElegirAsientos = { id, r, c, precio ->
                                        seatsEventoId = id
                                        seatsRows = r
                                        seatsCols = c
                                        checkoutPrecio = precio
                                    },
                                    onBack = { selectedEventoId = null }
                                )
                            }

                            /* ==================== VENTAS ==================== */
                            AppSection.VENTAS -> {
                                if (selectedVentaId != null) {
                                    val detVm = remember(selectedVentaId) {
                                        provideVentaDetalleViewModel()
                                    }

                                    VentaDetalleScreen(
                                        ventaId = selectedVentaId!!,
                                        sessionId = sessionId!!,
                                        vm = detVm,
                                        onBack = { selectedVentaId = null }
                                    )
                                } else {
                                    val ventasVm = remember { provideVentasViewModel() }

                                    VentasScreen(
                                        sessionId = sessionId!!,
                                        vm = ventasVm,
                                        onVentaClick = { id -> selectedVentaId = id }
                                    )
                                }
                            }

                            /* ==================== USUARIO ==================== */
                            AppSection.USUARIO -> {
                                UsuarioScreen(
                                    nombreUsuario = "Usuario logueado",
                                    onLogout = {
                                        scope.launch {
                                            try {
                                                api.cerrarSesion(sessionId!!)
                                            } catch (_: Exception) {
                                            }
                                            SessionStore.clear(ctx)
                                            sessionId = null
                                            section = AppSection.EVENTOS
                                            clearUiState()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}






