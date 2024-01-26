package devendrn.ecb.client.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import devendrn.ecb.client.data.UiState
import devendrn.ecb.client.ui.components.CardListItem
import devendrn.ecb.client.ui.home.components.TimetableItem

@Composable
fun EcHome(
    state: UiState,
    onItemClick: (HomeDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardItems = HomeDestination.entries
    Column(modifier = modifier) {
        TimetableItem(
            timetable = state.timeTable
        )
        cardItems.forEach { item ->
        CardListItem(
                label = stringResource(item.titleId),
                icon = item.icon,
                onClick = { onItemClick(item) }
            )
        }
    }
}
