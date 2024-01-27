package devendrn.ecb.client.ui.news

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import devendrn.ecb.client.ui.components.EcEmptyScreen
import devendrn.ecb.client.ui.theme.EcTheme

@Composable
fun ECBNews(modifier: Modifier = Modifier) {
    EcEmptyScreen(
        icon = Icons.Outlined.Newspaper,
        label = "Not Implemented",
        modifier = modifier
    )
}

@Preview(showBackground = true, widthDp = 300, heightDp = 600)
@Composable
fun NewsPreview() {
    EcTheme {
        ECBNews(Modifier.fillMaxSize())
    }
}
