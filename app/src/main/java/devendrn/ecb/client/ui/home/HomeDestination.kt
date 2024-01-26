package devendrn.ecb.client.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Grading
import androidx.compose.material.icons.outlined.HowToReg
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.ui.graphics.vector.ImageVector
import devendrn.ecb.client.R

enum class HomeDestination(
    @StringRes val titleId: Int,
    val icon: ImageVector
) {
    ATTENDANCE(R.string.attendance, Icons.Outlined.HowToReg),
    SERIES(R.string.series, Icons.Outlined.Grading),
    ASSIGNMENTS(R.string.assignments, Icons.Outlined.Assignment),
    INTERNALS(R.string.internals, Icons.Outlined.ListAlt)
}
