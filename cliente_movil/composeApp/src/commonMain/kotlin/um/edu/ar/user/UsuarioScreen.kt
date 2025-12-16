package um.edu.ar.user

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UsuarioScreen(
    nombreUsuario: String,
    onLogout: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(12.dp)) {

        Text("Usuario", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        Text("Nombre: $nombreUsuario")

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Cerrar sesión")
        }
    }
}
