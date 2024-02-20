package devendrn.ecb.client.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import devendrn.ecb.client.ui.navigation.EcTopLevelDestination
import devendrn.ecb.client.ui.theme.EcTheme

@Composable
fun EcNavBar(
    destinations: List<EcTopLevelDestination>,
    currentDestination: EcTopLevelDestination,
    onNavigateToDestination: (EcTopLevelDestination) -> Unit
) {
    NavigationBar {
        destinations.forEach { item ->
            val isSelected = item == currentDestination
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
                            item.iconFilled
                        } else item.iconOutlined,
                        contentDescription = stringResource(item.titleId)
                    )
                },
                label = { Text(stringResource(item.titleId)) },
                alwaysShowLabel = false,
                selected = isSelected,
                onClick = { onNavigateToDestination(item) }
            )
        }
    }
}

@Preview
@Composable
fun EcNavBarPreview() {
    EcTheme {
        var selectedItem by remember { mutableStateOf(EcTopLevelDestination.HOME) }
        EcNavBar(
            destinations = EcTopLevelDestination.entries,
            currentDestination = selectedItem,
            onNavigateToDestination = { selectedItem = it }
        )
    }
}