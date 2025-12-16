package um.edu.ar.login

import um.edu.ar.data.BASE_URL
import um.edu.ar.data.BackendApi

fun provideLoginViewModel(): LoginViewModel {
    val api = BackendApi(BASE_URL)
    return LoginViewModel(api)
}
