package devendrn.ecb.client.ui.profile.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import devendrn.ecb.client.R
import devendrn.ecb.client.ui.components.EcAppIcon
import devendrn.ecb.client.ui.components.EcCard
import devendrn.ecb.client.ui.theme.EcTheme

@Composable
fun EcAbout(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(horizontal = 15.dp)
    ) {
        EcCard(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(15.dp)
            ) {
                EcAppIcon()
                Column(
                    modifier = Modifier.padding(start = 15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(R.string.version) + " 1.0.1"
                    )
                }
            }
        }
        val links = listOf(
            (Icons.Outlined.Code to R.string.source_code) to "Link",
            (Icons.Outlined.BugReport to R.string.report_bug) to "Link",
            (Icons.Outlined.Key to R.string.license) to "Link"
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
        ) {
            links.forEach { (item, link) ->
                EcCard(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(15.dp)
                    ) {
                        val label = stringResource(item.second)
                        Icon(
                            imageVector = item.first,
                            contentDescription = label,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        Text(
                            text = label,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        EcCard {
            // TODO - move this list elsewhere
            val contributors = listOf(
                "Devendran S S" to R.string.main_developer
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.contributors),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 15.dp, start = 15.dp, end = 15.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .clip(MaterialTheme.shapes.medium)
                ) {
                    contributors.forEach {
                        ListItem(
                            headlineContent = { Text(it.first) },
                            supportingContent = { Text(stringResource(it.second)) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EcAboutPreview() {
    EcTheme {
        EcAbout()
    }
}