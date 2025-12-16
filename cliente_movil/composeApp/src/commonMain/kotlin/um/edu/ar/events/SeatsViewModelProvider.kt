package um.edu.ar.events

import um.edu.ar.data.BackendApi
import um.edu.ar.seats.SeatsViewModel
private const val BASE_URL = "http://10.0.2.2:8080"

fun provideSeatsViewModel(): SeatsViewModel {
    val api = BackendApi(BASE_URL)
    return SeatsViewModel(api)
}
