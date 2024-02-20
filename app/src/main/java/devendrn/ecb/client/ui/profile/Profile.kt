package devendrn.ecb.client.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import devendrn.ecb.client.R
import devendrn.ecb.client.data.ProfileDetails
import devendrn.ecb.client.network.NetworkUrl
import devendrn.ecb.client.ui.components.CardListItem
import devendrn.ecb.client.ui.components.EcCard
import devendrn.ecb.client.ui.theme.EcTheme

@Composable
fun EcProfile(
    profile: ProfileDetails,
    onItemClick: (ProfileDestination) -> Unit,
    onSignOutClick: () -> Unit,
    onChangePassClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        ProfileCardItem(
            profile.name,
            profile.branch,
            profile.semesterNo,
            profile.picUrl
        )
        val listItems = ProfileDestination.entries
        listItems.forEach { item ->
            CardListItem(stringResource(item.titleId), item.icon) {
                onItemClick(item)
            }
        }
        LoginActionsItem(
            username = "${profile.admissionNo}",
            onSignOutClick = onSignOutClick,
            onChangePassClick = onChangePassClick
        )
    }
}

@Composable
private fun ProfileCardItem(
    name: String,
    branch: String,
    semester: Int,
    profilePicUrl: String = "",
    modifier: Modifier = Modifier
) {
    EcCard(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(15.dp)
        ) {
            AsyncImage(
                model = profilePicUrl,
                placeholder = painterResource(R.drawable.profile_placeholder),
                contentDescription = stringResource(R.string.profile_pic),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 15.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "$branch Semester $semester"
                )
            }
        }
    }
}

@Composable
private fun LoginActionsItem(
    username: String,
    onSignOutClick: () -> Unit,
    onChangePassClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    EcCard(
        modifier = modifier.padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 15.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${NetworkUrl.DOMAIN}: $username",
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row(
                modifier = Modifier.padding(horizontal = 3.dp)
            ) {
                OutlinedButton(
                    onClick = onChangePassClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 15.dp)
                ) {
                    Text(stringResource(R.string.change_pass))
                }
                Button(
                    onClick = onSignOutClick
                ) {
                    Text(stringResource(R.string.sign_out))
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileCardPreview() {
    EcTheme {
        ProfileCardItem(
            name = "Maxim Nausea",
            branch = "B.Tech CAT",
            semester = 4,
            profilePicUrl = "https://photos.costume-works.com/thumbs/monster_cat.jpg"
        )
    }
}