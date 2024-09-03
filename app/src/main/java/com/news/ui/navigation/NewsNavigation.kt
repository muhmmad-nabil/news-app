package com.news.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.news.domain.entity.Article
import com.news.ui.screens.article_details.ArticleDetailsScreen
import com.news.ui.screens.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NewsScreens.Home.name) {
        composable(NewsScreens.Home.name) {
            HomeScreen(navController, koinViewModel())
        }

        composable(
            NewsScreens.ArticleDetails.name + "/{article}",
            arguments = listOf(navArgument("article") {
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString("article")?.let {
                val article = Gson().fromJson(it, Article::class.java)
                ArticleDetailsScreen(navController, article)
            }
        }
    }

}