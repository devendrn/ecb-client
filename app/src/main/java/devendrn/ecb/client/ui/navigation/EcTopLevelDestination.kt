package devendrn.ecb.client.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import devendrn.ecb.client.R

enum class EcTopLevelDestination(
    @StringRes val titleId: Int,
    val iconFilled: ImageVector,
    val iconOutlined: ImageVector
) {
    HOME(R.string.home, Icons.Filled.Home, Icons.Outlined.Home),
    NEWS(R.string.ktu_news, Icons.Filled.Email, Icons.Outlined.Email),
    PROFILE(R.string.profile, Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle),
}