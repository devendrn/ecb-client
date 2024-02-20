package devendrn.ecb.client.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import devendrn.ecb.client.ui.theme.EcTheme

@Composable
fun StatusIndicator(
    isOnline: Boolean,
    activity: String?,
    lastUpdate: String,
    modifier: Modifier = Modifier
) {
    val message: String
    val iconVector: ImageVector

    val isLoading = activity != null
    if (isLoading) {
        iconVector = Icons.Outlined.Autorenew
        message = activity?: " - "
    } else if (isOnline) {
        iconVector = Icons.Outlined.Bedtime
        message = "Idle"
    } else {
        iconVector = Icons.Outlined.WifiOff
        message = "You're Offline!"
    }

    val primaryCol: Color = MaterialTheme.colorScheme.primary
    val errorCol: Color = MaterialTheme.colorScheme.error

    val statusCol by animateColorAsState(
        targetValue = if (!isOnline) errorCol else primaryCol,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        ),
        label = "status color"
    )

    val trackCol by animateColorAsState(
        targetValue = if (isLoading) Color.Transparent else statusCol,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        ),
        label = "track color"
    )

    val targetWidth = if (!isOnline) 7f else 3f
    val infiniteTransition = rememberInfiniteTransition("breathing transition")
    val width by infiniteTransition.animateFloat(
        initialValue = 3f,
        targetValue = targetWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                delayMillis = 600,
                easing = EaseInOutCubic
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Breath animation"
    )

    var statusMenuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        IconButton(
            onClick = { statusMenuExpanded = true }
        ) {
            CircularProgressIndicator(
                color = statusCol,
                trackColor = trackCol,
                strokeWidth = width.dp,
                modifier = Modifier.size((30 - width).dp)
            )
        }
        StatusIndicatorPopup(
            isExpanded = statusMenuExpanded,
            color = statusCol,
            icon = iconVector,
            message = message,
            timestamp = lastUpdate,
            onClose = { statusMenuExpanded = false }
        )
    }
}

@Composable
private fun StatusIndicatorPopup(
    icon: ImageVector,
    isExpanded: Boolean,
    color: Color,
    message: String,
    timestamp: String,
    onClose: () -> Unit
) {
    val expandedStates = remember { MutableTransitionState(false) }
    expandedStates.targetState = isExpanded
    if (expandedStates.currentState || expandedStates.targetState) {
        Popup(
            onDismissRequest = onClose,
            offset = IntOffset(25, 85),
            properties = PopupProperties(focusable = true),
        ) {
            val transition = updateTransition(expandedStates, "DropDownMenu")

            val inTransitionDuration = 250
            val outTransitionDuration = 200
            val tr by transition.animateFloat(
                transitionSpec = {
                    if (false isTransitioningTo true) {
                        // Dismissed to expanded
                        tween(
                            durationMillis = inTransitionDuration,
                            easing = LinearOutSlowInEasing
                        )
                    } else {
                        // Expanded to dismissed.
                        tween(
                            durationMillis = outTransitionDuration
                        )
                    }
                },
                label = "Popup transition"
            ) {
                if (it) 1f else 0.0f
            }

            StatusIndicatorPopupContent(
                icon = icon,
                message = message,
                timestamp = timestamp,
                color = color,
                modifier = Modifier.graphicsLayer {
                    translationX = -120f * (1f - tr)
                    translationY = -100f * (1f - tr)
                    scaleX = 0.5f + 0.5f * tr
                    scaleY = 0.8f + 0.2f * tr
                    alpha = tr
                },
            )
        }
    }
}

@Composable
private fun StatusIndicatorPopupContent(
    icon: ImageVector,
    message: String,
    timestamp: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        contentColor = MaterialTheme.colorScheme.onPrimary,
        color = color,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .width(250.dp)
                .padding(15.dp)
        ) {
            Row{
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(message)
            }
            Divider(
                thickness = 1.5f.dp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
            )
            Text(
                text = "Last update: $timestamp",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatusIndicatorPreview() {
    EcTheme {
        Row {
            StatusIndicator(true, null, "")
            StatusIndicator(false, null, "")
            StatusIndicator(true, "url", "")
        }
    }
}

@Preview
@Composable
fun StatusPopupPreviewError() {
    EcTheme {
        StatusIndicatorPopupContent(
            icon = Icons.Outlined.WifiOff,
            color = MaterialTheme.colorScheme.error,
            message = "You're offline!",
            timestamp = "11:43 AM June 14"
        )
    }
}

@Preview
@Composable
fun StatusPopupPreviewIdle() {
    EcTheme {
        StatusIndicatorPopupContent(
            icon = Icons.Outlined.Bedtime,
            color = MaterialTheme.colorScheme.primary,
            message = "Idle (Connected)",
            timestamp = "1:43 PM Dec 14"
        )
    }
}

@Preview
@Composable
fun StatusPopupPreviewLoading() {
    EcTheme {
        StatusIndicatorPopupContent(
            icon = Icons.Outlined.Autorenew,
            color = MaterialTheme.colorScheme.primary,
            message = "students/academics",
            timestamp = "1:43 PM Dec 14"
        )
    }
}
