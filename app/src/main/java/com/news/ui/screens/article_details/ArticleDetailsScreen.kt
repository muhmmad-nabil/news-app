package com.news.ui.screens.article_details

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.news.data.entity.Article

private const val TAG = "ArticleDetailsScreen"

@Composable
fun ArticleDetailsScreen(navController: NavController, article: Article) {
    Log.i(TAG, article.toString())

}