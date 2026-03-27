
package um.edu.ar.login

import um.edu.ar.model.AsientoSeleccionado
import um.edu.ar.ui.AppSection
import um.edu.ar.model.SessionStateResponse

fun restoreFromState(
    state: SessionStateResponse,
    setSection: (AppSection) -> Unit,
    setEvento: (Long?) -> Unit,
    setSeatsEvento: (Long?) -> Unit,
    setCheckoutEvento: (Long?) -> Unit,
    setCheckoutSeats: (List<AsientoSeleccionado>) -> Unit,
    setVenta: (Long?) -> Unit
) {
    when (state.pasoActual) {

        "LISTA_EVENTOS" -> {
            setSection(AppSection.EVENTOS)
        }

        "DETALLE_EVENTO" -> {
            setSection(AppSection.EVENTOS)
            setEvento(state.eventoId)
        }

        "SELECCION_ASIENTOS" -> {
            setSection(AppSection.EVENTOS)
            setSeatsEvento(state.eventoId)
        }

        "CARGA_NOMBRES" -> {
            setSection(AppSection.EVENTOS)
            setCheckoutEvento(state.eventoId)
            setCheckoutSeats(state.asientos ?: emptyList())
        }

        "VENTA_DETALLES" -> {
            setSection(AppSection.VENTAS)
            setVenta(state.ventaId)
        }

        "VENTA_COMPLETADA" -> {
            setSection(AppSection.VENTAS)
        }

        else -> setSection(AppSection.EVENTOS)
    }
}
