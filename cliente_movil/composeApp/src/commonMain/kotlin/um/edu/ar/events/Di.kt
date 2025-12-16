package um.edu.ar.events

import um.edu.ar.data.BASE_URL
import um.edu.ar.data.BackendApi

fun provideEventListViewModel(sessionId: String): EventListViewModel {
    val api = BackendApi(BASE_URL)
    return EventListViewModel(api, sessionId)
}
fun provideEventDetailViewModel(sessionId: String, eventoId: Long): EventDetailViewModel {
    val api = BackendApi(BASE_URL)
    return EventDetailViewModel(api, sessionId, eventoId)
}
