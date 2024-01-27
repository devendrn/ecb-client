package devendrn.ecb.client.ui.home.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EventBusy
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import devendrn.ecb.client.R
import devendrn.ecb.client.data.LabeledFraction
import devendrn.ecb.client.ui.components.EcEmptyScreen
import devendrn.ecb.client.ui.components.EcLazyValueList
import devendrn.ecb.client.ui.theme.EcTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EcSeries(
    selectedSeries: Int,
    entries1: List<LabeledFraction>,
    entries2: List<LabeledFraction>,
    onTabChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedTab = selectedSeries - 1
    val series = listOf("First", "Second")
    val seriesEntries = listOf(entries1, entries2)

    Column(modifier = modifier) {
        val pagerState = rememberPagerState(
            initialPage = selectedTab,
            pageCount = { seriesEntries.size }
        )
        val coroutineScope = rememberCoroutineScope()

        TabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
            series.forEachIndexed { index, title ->
                Tab(
                    selected = index == pagerState.currentPage,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                        onTabChange(index + 1)
                    },
                    text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {page ->
            if(seriesEntries[page].isEmpty()) {
                EcEmptyScreen(
                    icon = Icons.Outlined.EventBusy,
                    label = stringResource(R.string.no_results_pub)
                )
            } else {
                EcLazyValueList(seriesEntries[page])
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EcSeriesPreview() {
    EcTheme {
        var selectedSeries by remember { mutableStateOf(1) }
        EcSeries(
            selectedSeries = selectedSeries,
            entries1 = listOf(),
            entries2 = listOf(
                LabeledFraction("Control System", 39, 50),
                LabeledFraction("Disaster Management", 41, 50),
                LabeledFraction("Digital Signal Processing", 43, 50)
            ),
            onTabChange = { selectedSeries = it }
        )
    }
}