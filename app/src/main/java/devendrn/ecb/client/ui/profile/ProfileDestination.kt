package devendrn.ecb.client.ui.profile

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import devendrn.ecb.client.R

enum class ProfileDestination(
    @StringRes val titleId: Int,
    val icon: ImageVector
) {
    INFO(R.string.details, Icons.Outlined.AccountCircle),
    SETTINGS(R.string.settings, Icons.Outlined.Settings),
    ABOUT(R.string.about, Icons.Outlined.Info)
}