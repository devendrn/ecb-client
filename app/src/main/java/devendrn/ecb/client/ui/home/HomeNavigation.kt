package devendrn.ecb.client.ui.home

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import devendrn.ecb.client.model.Day
import devendrn.ecb.client.model.TimeTable
import devendrn.ecb.client.ui.home.screens.EcAssignments
import devendrn.ecb.client.ui.home.screens.EcAttendance
import devendrn.ecb.client.ui.home.screens.EcInternals
import devendrn.ecb.client.ui.home.screens.EcKtuResults
import devendrn.ecb.client.ui.home.screens.EcSeries

const val HOME_ROUTE = "home"

const val HOME_START_ROUTE = "home/start"
const val HOME_ATTENDANCE_ROUTE = "home/attendance"
const val HOME_ASSIGNMENTS_ROUTE = "home/assignments"
const val HOME_SERIES_ROUTE = "home/series"
const val HOME_INTERNALS_ROUTE = "home/internals"
const val HOME_KTU_RESULTS_ROUTE = "home/ktu_results"

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreenRoute(
    onPageClick: (String) -> Unit,
    homeViewModel: HomeViewModel
) {
    navigation(
        startDestination = HOME_START_ROUTE,
        route = HOME_ROUTE
    ) {
        composable(route = HOME_START_ROUTE) {
            EcHome(
                timetable = homeViewModel.timetableEntries.collectAsState(
                    // TODO - maybe use null instead?
                    initial = TimeTable(
                        currentDate = 0,
                        currentDay = Day.MON,
                        currentMonth = "June",
                        map = mapOf()
                    )
                ).value,
                onItemClick = { destination ->
                    when(destination) {
                        HomeDestination.ATTENDANCE -> {
                            homeViewModel.updateSubjectEntries()
                            onPageClick(HOME_ATTENDANCE_ROUTE)
                        }
                        HomeDestination.SERIES -> onPageClick(HOME_SERIES_ROUTE)
                        HomeDestination.ASSIGNMENTS -> onPageClick(HOME_ASSIGNMENTS_ROUTE)
                        HomeDestination.INTERNALS -> onPageClick(HOME_INTERNALS_ROUTE)
                        HomeDestination.KTU_RESULTS -> {
                            homeViewModel.updateKtuExamEntries()
                            onPageClick(HOME_KTU_RESULTS_ROUTE)
                        }
                    }
                }
            )
        }
        composable(route = HOME_ATTENDANCE_ROUTE) {
            EcAttendance(homeViewModel.attendanceEntries.collectAsState(initial = emptyList()).value)
        }
        composable(route = HOME_ASSIGNMENTS_ROUTE) {
            EcAssignments(homeViewModel.uiState.assignmentsEntries)
        }
        composable(route = HOME_SERIES_ROUTE) {
            val uiState = homeViewModel.uiState
            EcSeries(
                selectedSeries = uiState.defaultSeriesSelection,
                onTabChange = { },
                entries1 = uiState.series1Entries,
                entries2 = uiState.series2Entries
            )
        }
        composable(route = HOME_INTERNALS_ROUTE) {
            EcInternals(homeViewModel.uiState.internalsEntries)
        }
        composable(route = HOME_KTU_RESULTS_ROUTE) {
            EcKtuResults(
                profileDetails = homeViewModel.ktuProfile,
                ktuExams = homeViewModel.ktuExams,
                ktuExamResults = homeViewModel.ktuExamResults,
                ktuExamResultsLoading = homeViewModel.ktuResultLoading,
                ktuExamSelected = homeViewModel.ktuExamSelected,
                onSelectExam = homeViewModel::updateKtuExamResultEntries
            )
        }

    }
}