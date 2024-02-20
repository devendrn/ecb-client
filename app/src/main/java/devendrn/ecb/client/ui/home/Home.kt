package devendrn.ecb.client.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import devendrn.ecb.client.R
import devendrn.ecb.client.model.TimeTable
import devendrn.ecb.client.ui.components.EcCard
import devendrn.ecb.client.ui.home.components.TimetableItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EcHome(
    timetable: TimeTable,
    onItemClick: (HomeDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardItems = HomeDestination.entries

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp)
    ) {
        EcCard {
            AsyncImage(
                model = "https://tkmce.etlab.in/uploads/webpopups/Slider%207-1625039997.jpg",
                placeholder = painterResource(R.drawable.profile_placeholder),
                contentDescription = stringResource(R.string.profile_pic),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(110.dp)
            )
        }
        TimetableItem(timetable)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            cardItems.forEach { item ->
                EcCard(
                    onClick = { onItemClick(item) },
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(65.dp)
                            .padding(15.dp)
                    ) {
                        Icon(item.icon, contentDescription = null)
                        Text(
                            text = stringResource(id = item.titleId),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }
        }
    }
}
