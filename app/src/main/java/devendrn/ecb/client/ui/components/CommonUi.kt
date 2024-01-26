package devendrn.ecb.client.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import devendrn.ecb.client.R
import devendrn.ecb.client.data.LabeledFraction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListItem(
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    EcCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            Text(
                text = label,
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValueListCard(
    label: String,
    value: Int,
    total: Int,
    showPercent: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    EcCard(
        onClick = onClick,
        modifier = modifier.height(85.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = modifier
                    .weight(1f)
                    .padding(end = 15.dp)
            )
            Text(
                text = "$value/$total",
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = value / total.toFloat(),
                    modifier = Modifier.size(
                        if (showPercent) 50.dp else 25.dp
                    )
                )
                if (showPercent) {
                    Text("${(value * 100) / total}")
                }
            }
        }
    }
}

@Composable
fun EcAppIcon(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(80.dp)
            .clip(CircleShape)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
        )
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
    }
}

@Composable
fun EcEmptyScreen(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = icon,
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .padding(bottom = 10.dp)
        )
        Text(
            color = MaterialTheme.colorScheme.secondary,
            text = label,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun EcCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        content = content,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        content = content,
        modifier = modifier
    )
}

@Composable
fun EcLazyValueList(
    entries: List<LabeledFraction>,
    showPercent: Boolean = true,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(10.dp),
        modifier = modifier
    ) {
        items(entries) { entry ->
            ValueListCard(
                label = entry.label,
                value = entry.value,
                total = entry.total,
                showPercent = showPercent,
                onClick = { }
            )
        }
    }
}