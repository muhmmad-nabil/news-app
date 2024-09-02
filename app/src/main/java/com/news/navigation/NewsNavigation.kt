package com.news.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.news.screens.ArticleDetailsScreen
import com.news.screens.home.HomeScreen

@Composable
fun NewsNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NewsScreens.Home.name) {
        composable(NewsScreens.Home.name) {
            HomeScreen(navController)
        }

        composable(NewsScreens.ArticleDetails.name) {
            ArticleDetailsScreen(navController)
        }
    }

}