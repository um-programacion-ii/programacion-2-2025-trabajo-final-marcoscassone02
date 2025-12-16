package um.edu.ar.sells
import um.edu.ar.data.BASE_URL
import um.edu.ar.data.BackendApi

fun provideVentasViewModel(): VentasViewModel {
    return VentasViewModel(
        api = BackendApi(BASE_URL)
    )
}

fun provideVentaDetalleViewModel(): VentaDetalleViewModel {
    return VentaDetalleViewModel(
        api = BackendApi(BASE_URL)
    )
}

fun provideCheckoutViewModel(): CheckoutViewModel {
    return CheckoutViewModel(
        api = BackendApi(BASE_URL)
    )
}
