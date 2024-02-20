package devendrn.ecb.client.ui.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import devendrn.ecb.client.model.KtuAnnouncement
import devendrn.ecb.client.model.KtuAnnouncementAttachment
import devendrn.ecb.client.ui.components.EcCard
import devendrn.ecb.client.ui.components.EcEmptyScreen
import devendrn.ecb.client.ui.theme.EcTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ECBNews(
    announcements: List<KtuAnnouncement>,
    modifier: Modifier = Modifier
) {
    Column {
        /*
        SearchBar(
            query = "",
            onQueryChange = { },
            onSearch = { },
            active = false,
            onActiveChange = { },
            placeholder = { Text("Search") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        ) { }*/
        if (announcements.isEmpty()) {
            EcEmptyScreen(icon = Icons.Outlined.BrokenImage, label = "No announcements found")
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 10.dp),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            items(announcements) { item ->
                EcNotificationCard(
                    title = item.title,
                    message = item.message,
                    date = item.date,
                    attachments = item.attachments
                )
            }
        }
    }
}

@Composable
private fun EcNotificationCard(
    title: String,
    message: String,
    date: String,
    attachments: List<KtuAnnouncementAttachment>
) {
    EcCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            if (message.isNotEmpty()) {
                Text(text = message, style = MaterialTheme.typography.bodyMedium)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    attachments.forEach {
                        EcAttachmentChip(text = it.title)
                    }
                }
            }
        }
    }
}
@Composable
private fun EcAttachmentChip(text: String) {
    Surface(
        tonalElevation = 0.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp)
                    .padding(end = 5.dp)
            )
            Text(text = text, style = MaterialTheme.typography.bodySmall)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun NewsPreview() {
    EcTheme {
        ECBNews(
            announcements = listOf(
                KtuAnnouncement(
                    "PROVISIONAL LIST OF SELECTED CANDIDATES FDP ON COUNSELLING AND ACADEMIC MENTORING-Ernakulam",
                    "",
                    "January 29, 2024",
                    listOf(
                        KtuAnnouncementAttachment("Notification", "", "")
                    )
                ),
                KtuAnnouncement(
                    "List of selected participants for the AoT workshop",
                    "List of students selected for the workshop on Aesthetics of Technology of Things (AoT) to be held at GEC Thrissur from 19th to 21st of February 2024",
                    "January 29, 2024",
                    listOf(
                        KtuAnnouncementAttachment("Notification 1", "", ""),
                        KtuAnnouncementAttachment("Notification 2", "", "")
                    )
                ),
                KtuAnnouncement(
                    "Applications are invited to the post of Finance Officer",
                    "Applications are invited to the following positions/posts on Regular/Deputation basis in the APJ Abdul Kalam Technological University, details of which are as follows: 1. Finance Officer - 1 Post The qualification and experience for the above positions/posts have been prescribed in the First Statutes of the APJ Abdul Kalam Technological University, the original of which was issued in Malayalam. Applicants shall verify and confirm their eligibility by referring to the original of the First Statutes available on the website of the University before submission of applications.",
                    "January 29, 2024",
                    listOf(
                        KtuAnnouncementAttachment("Notification", "", "")
                    )
                )
            )
        )
    }
}
