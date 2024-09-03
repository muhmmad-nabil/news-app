package com.news.ui.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.news.data.entity.Article
import com.news.ui.components.ErrorSnackBar
import com.news.ui.navigation.NewsScreens
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private const val TAG = "HomeScreen"

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val viewState by viewModel.state.collectAsStateWithLifecycle(HomeViewState.Idle)


    val errorSnackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val news = remember {
        mutableStateOf(emptyList<Article>())
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (viewState) {
            is HomeViewState.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.Center)
            )

            is HomeViewState.Error -> {
                coroutineScope.launch {
                    errorSnackState.showSnackbar(
                        (viewState as HomeViewState.Error).error,
                        duration = SnackbarDuration.Short
                    )
                }
            }

            is HomeViewState.Result -> {
                news.value = (viewState as HomeViewState.Result).data
            }

            HomeViewState.Idle -> {

            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            LazyRow {
                items(viewModel.categories.size) { index ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            viewModel.intentChannel.trySend(HomeIntent.getNews(viewModel.categories[index]))
                        }) {
                        Text(
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            text = viewModel.categories[index],
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp)
                        )
                        if (index != viewModel.categories.lastIndex) VerticalDivider(
                            modifier = Modifier.height(
                                20.dp
                            )
                        )
                    }

                }
            }

            LazyColumn {
                items(news.value.size) { index ->
                    val item = news.value[index]
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            item.url =
                                URLEncoder.encode(item.url, StandardCharsets.UTF_8.toString())

                            item.urlToImage = URLEncoder.encode(
                                item.urlToImage,
                                StandardCharsets.UTF_8.toString()
                            )

                            navController.navigate(
                                NewsScreens.ArticleDetails.name + "/${Gson().toJson(item)}"
                            )
                        }) {
                        AsyncImage(
                            modifier = Modifier.size(120.dp),
                            model = item.urlToImage,
                            contentDescription = null
                        )
                        Column {
                            Text(
                                color = Color.Black,
                                text = item.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(10.dp),
                            )

                            Text(text = "author : ${item.author}")

                            Text(text = item.publishedAt)
                        }
                    }
                }
            }
        }


        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            hostState = errorSnackState
        ) { snackbarData: SnackbarData ->
            ErrorSnackBar(
                snackbarData.visuals.message
            )
        }
    }
}