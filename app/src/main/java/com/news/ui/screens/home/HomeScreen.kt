package com.news.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.news.domain.entity.Article
import com.news.ui.components.ErrorSnackBar
import com.news.ui.navigation.NewsScreens
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val viewState by viewModel.state.collectAsStateWithLifecycle(HomeViewState.Idle)

    val errorSnackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val news = remember {
        mutableStateOf(emptyList<Article>())
    }
    val selectedCategoryIndex = remember {
        mutableIntStateOf(0)
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

            ScrollableTabRow(
                selectedTabIndex = selectedCategoryIndex.intValue,
                edgePadding = 0.dp
            ) {
                viewModel.categories.forEachIndexed { index, category ->
                    Tab(
                        selected = index == selectedCategoryIndex.intValue,
                        onClick = {
                            viewModel.intentChannel.trySend(HomeIntent.getNews(category))
                            selectedCategoryIndex.intValue = index
                        }) {
                        Text(
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            text = category,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(5.dp),
                        )
                    }
                }
            }

            LazyColumn {
                items(news.value.size) { index ->
                    val item = news.value[index]
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(5.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(20.dp))
                            .clickable {
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
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(200.dp)
                                .width(120.dp)
                                .padding(5.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            model = item.urlToImage,
                            contentDescription = null
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                maxLines = 2,
                                color = Color.Black,
                                text = item.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(10.dp),
                            )

                            if (item.author != null) Text(text = "author : ${item.author}")

                            Text(text = item.publishedAt.substring(0..9))
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