package devendrn.ecb.client.ui.home.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import devendrn.ecb.client.data.LabeledFraction
import devendrn.ecb.client.ui.components.EcLazyValueList

@Composable
fun EcInternals(entries: List<LabeledFraction>, modifier: Modifier = Modifier) {
    EcLazyValueList(entries)
}
