package devendrn.ecb.client.ui.profile.screens

import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EcSettings(

) {
    ListItem(
        headlineContent = { Text("Dynamic Theming") },
        supportingContent = { Text("Android 12+") },
        trailingContent = { Switch(checked = false, onCheckedChange = {}) }
    )
}