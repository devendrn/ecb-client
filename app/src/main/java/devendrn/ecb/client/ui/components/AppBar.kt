package devendrn.ecb.client.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import devendrn.ecb.client.R
import devendrn.ecb.client.ui.theme.EcTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcAppBar(
    @StringRes titleId: Int,
    profilePicUrl: String,
    showBackButton: Boolean = false,
    activityUrl: String? = null,
    showProfilePic: Boolean = true,
    navigateBack: () -> Unit,
    onIndicatorClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val status = if (activityUrl == null) {
        StatusIndicatorState.IDLE
    } else StatusIndicatorState.LOADING

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(titleId),
                maxLines = 1
            )
        },
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val density = LocalDensity.current
                AnimatedVisibility(
                    visible = showBackButton,
                    enter = expandHorizontally() + fadeIn(),
                    exit = shrinkHorizontally() + fadeOut()
                ) {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
                StatusIndicator(
                    status
                )
            }
        },
        actions = {
            if (showProfilePic) {
                IconButton(
                    onClick = onProfileClick,
                    modifier = Modifier.padding(end = 5.dp)
                ) {
                    AsyncImage(
                        model = profilePicUrl,
                        placeholder = painterResource(R.drawable.profile_placeholder),
                        contentDescription = stringResource(R.string.profile_pic),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun EcAppBarPreview() {
    EcTheme {
        EcAppBar(
            titleId = R.string.app_name,
            showBackButton = false,
            showProfilePic = true,
            navigateBack = { },
            profilePicUrl = "",
            onIndicatorClick = { },
            onProfileClick = { }
        )
    }
}