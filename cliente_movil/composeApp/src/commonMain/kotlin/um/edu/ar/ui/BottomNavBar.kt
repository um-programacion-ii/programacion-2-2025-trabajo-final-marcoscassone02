package um.edu.ar.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Person

@Composable
fun BottomNavBar(
    current: AppSection,
    onSelect: (AppSection) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = current == AppSection.EVENTOS,
            onClick = { onSelect(AppSection.EVENTOS) },
            icon = { Icon(Icons.Default.Event, null) },
            label = { Text("Eventos") }
        )

        NavigationBarItem(
            selected = current == AppSection.VENTAS,
            onClick = { onSelect(AppSection.VENTAS) },
            icon = { Icon(Icons.Default.Receipt, null) },
            label = { Text("Ventas") }
        )

        NavigationBarItem(
            selected = current == AppSection.USUARIO,
            onClick = { onSelect(AppSection.USUARIO) },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Usuario") }
        )
    }
}
