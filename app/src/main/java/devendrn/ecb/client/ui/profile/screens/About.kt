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
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import devendrn.ecb.client.BuildConfig
import devendrn.ecb.client.R
import devendrn.ecb.client.misc.contributors
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            EcAppIcon(
                modifier = Modifier.padding(bottom = 15.dp)
            )
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = stringResource(R.string.version) + " ${BuildConfig.VERSION_NAME}"
            )
        }

        val uriHandler = LocalUriHandler.current
        val links = listOf(
            (Icons.Outlined.Code to R.string.source_code) to
                    "https://github.com/devendrn/ecb-client",
            (Icons.Outlined.BugReport to R.string.report_bug) to
                    "https://github.com/devendrn/ecb-client/issues",
            (Icons.Outlined.Key to R.string.license) to
                    "https://github.com/devendrn/ecb-client/blob/main/LICENSE"
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
        ) {
            links.forEach { (item, link) ->
                EcCard(
                    onClick = { uriHandler.openUri(link) },
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
                    .padding(vertical = 15.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                contributors.forEach {
                    ListItem(
                        headlineContent = {
                            Text(it.name, Modifier.padding(start = 10.dp))
                        },
                        supportingContent = {
                            Text(it.role, Modifier.padding(start = 10.dp))
                        },
                        trailingContent = {
                            IconButton(onClick = { uriHandler.openUri(it.link) }) {
                                Icon(Icons.Outlined.OpenInNew, contentDescription = null)
                            }
                        },
                        tonalElevation = 4.dp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EcAboutPreview() {
    EcTheme {
        EcAbout()
    }
}