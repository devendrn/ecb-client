package devendrn.ecb.client.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import devendrn.ecb.client.data.UiState
import devendrn.ecb.client.ui.home.screens.EcAssignments
import devendrn.ecb.client.ui.home.screens.EcAttendance
import devendrn.ecb.client.ui.home.screens.EcInternals
import devendrn.ecb.client.ui.home.screens.EcSeries

const val HOME_ROUTE = "home"

const val HOME_START_ROUTE = "home/start"
const val HOME_ATTENDANCE_ROUTE = "home/attendance"
const val HOME_ASSIGNMENTS_ROUTE = "home/assignments"
const val HOME_SERIES_ROUTE = "home/series"
const val HOME_INTERNALS_ROUTE = "home/internals"

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreenRoute(
    onPageClick: (String) -> Unit
) {
    navigation(
        startDestination = HOME_START_ROUTE,
        route = HOME_ROUTE
    ) {
        // TODO - use DI
        val homeViewModel: HomeViewModel = HomeViewModel()
        val uiState: UiState = homeViewModel.uiState

        composable(route = HOME_START_ROUTE) {
            EcHome(
                state = homeViewModel.uiState,
                onItemClick = { destination ->
                    when(destination) {
                        HomeDestination.ATTENDANCE -> onPageClick(HOME_ATTENDANCE_ROUTE)
                        HomeDestination.SERIES -> onPageClick(HOME_SERIES_ROUTE)
                        HomeDestination.ASSIGNMENTS -> onPageClick(HOME_ASSIGNMENTS_ROUTE)
                        HomeDestination.INTERNALS -> onPageClick(HOME_INTERNALS_ROUTE)
                    }
                }
            )
        }
        composable(route = HOME_ATTENDANCE_ROUTE) {
            EcAttendance(uiState.attendanceEntries)
        }
        composable(route = HOME_ASSIGNMENTS_ROUTE) {
            EcAssignments(uiState.assignmentsEntries)
        }
        composable(route = HOME_SERIES_ROUTE) {
            EcSeries(
                selectedSeries = uiState.defaultSeriesSelection,
                onTabChange = { },
                entries1 = uiState.series1Entries,
                entries2 = uiState.series2Entries
            )
        }
        composable(route = HOME_INTERNALS_ROUTE) {
            EcInternals(uiState.internalsEntries)
        }
    }
}