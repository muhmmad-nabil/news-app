package com.news.ui.screens.article_details

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.news.R
import com.news.data.entity.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsScreen(
    navController: NavController,
    article: Article

) {
    val context = LocalContext.current
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, article.url)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(
        sendIntent,
        context.getString(R.string.share_article)
    )

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(R.string.article_details),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                fontSize = 20.sp
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = ""
                )
            }
        },
            actions = {
                Image(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable {
                            startActivity(context, shareIntent, null)
                        })
            })
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                model = article.urlToImage,
                contentDescription = stringResource(R.string.article_image)
            )

            Text(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 5.dp),
                text = article.title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                maxLines = 2
            )

            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                text = article.description,
                color = Color.Black,
            )


            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                text = article.content,
                color = Color.Black,
            )
        }
    }
}