package devendrn.ecb.client.ui.profile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import devendrn.ecb.client.data.UiState
import devendrn.ecb.client.ui.theme.EcTheme

@Composable
fun EcDetails(
    details: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(details) {
            DetailsItem(label = it.first, value = it.second)
        }
    }
}

@Composable
private fun DetailsItem(
    label: String,
    value: String
) {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(
                text = value,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }
        Divider()
    }
}

@Preview
@Composable
fun EcDetailsPreview() {
    EcTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            EcDetails(UiState().personalDetails)
        }
    }
}