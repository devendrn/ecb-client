package devendrn.ecb.client.ui.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import devendrn.ecb.client.R
import devendrn.ecb.client.model.Day
import devendrn.ecb.client.model.TimeTable
import devendrn.ecb.client.ui.theme.EcTheme


@Composable
fun TimetableItem(
    timetable: TimeTable,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Text(
                    text = stringResource(R.string.timetable),
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { isExpanded = !isExpanded }
                ) {
                    Icon(
                        imageVector = if (isExpanded) {
                            Icons.Filled.ExpandLess
                        } else {
                            Icons.Filled.ExpandMore
                        },
                        contentDescription = null
                    )
                }
            }
            Divider()
            Text(
                text = "${timetable.currentDay.word}, ${timetable.currentMonth} ${timetable.currentDate}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(10.dp)
            )
            Divider()
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                timetable.map.forEach { entry ->
                    if (isExpanded || timetable.currentDay == entry.key) {
                        TimetableRow("${entry.key.firstChar()}", entry.value)
                    }
                }
            }
        }
    }
}

@Composable
private fun TimetableRow(
    day: String,
    subjects: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.height(35.dp)
    ) {
        Text(
            text = day,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.width(32.dp)
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 0.dp, horizontal = 6.dp)
            ) {
                subjects.forEachIndexed { index, subject ->
                    Text(
                        text = subject,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center
                    )
                    if (index < subjects.size - 1) {
                        Divider(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun TimetablePrev() {
    EcTheme {
        TimetableItem(
            timetable = TimeTable(
                currentDate = 17,
                currentDay = Day.MON,
                currentMonth = "June",
                map = mapOf(
                    Day.MON to listOf("DSP", "DSP", "LIC", "MOE", "ADC", "CS"),
                    Day.TUE to listOf("ADC", "ADC", "DM", "ADC", "MOE", "DSP"),
                    Day.WED to listOf("MOE", "LIC", "ADC", "CS", "DSP", "DM"),
                    Day.THU to listOf("CS", "ADC", "LIC", "LIC", "MOE", "DSP"),
                    Day.FRI to listOf("DM", "ADC", "MOE", "MOE", "ADC", "ADC")
                )
            )
        )
    }
}