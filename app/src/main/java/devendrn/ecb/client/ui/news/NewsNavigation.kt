package devendrn.ecb.client.ui.news

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val NEWS_ROUTE = "news"

const val NEWS_START_ROUTE = "news/start"

fun NavController.navigateToNews(topLevelNavOptions: NavOptions) = navigate(NEWS_ROUTE, topLevelNavOptions)

fun NavGraphBuilder.newsScreenRoute() {
    navigation(
        route = NEWS_ROUTE,
        startDestination = NEWS_START_ROUTE
    ) {
        composable(route = NEWS_START_ROUTE) {
            ECBNews()
        }
    }
}