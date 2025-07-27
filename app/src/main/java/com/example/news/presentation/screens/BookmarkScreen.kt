package com.example.news.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.news.R
import com.example.news.presentation.model.DetailRoute
import com.example.news.presentation.model.UIState
import com.example.news.presentation.model.news.ArticleUi
import com.example.news.presentation.viewmodels.BookmarkViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

@Composable
fun BookmarkScreen(
    paddingValues: PaddingValues,
    viewModel: BookmarkViewModel = hiltViewModel(),
    navController: NavController,
) {


    val news by viewModel.news
    val context = LocalContext.current
    val currentDate by viewModel.currentDate

    LaunchedEffect(Unit) {
        viewModel.getNews()
        viewModel.getCurrentDate()
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 33.dp, start = 27.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
            )
            Text(
                text = stringResource(R.string.news_catcher),
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(start = 10.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }


        Text(
            text = currentDate,
            color = colorResource(R.color.tv_grey),
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 27.dp, top = 31.dp, end = 27.dp),
            fontWeight = FontWeight.Medium
        )
        when (news) {
            is UIState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.tv_grey),
                        strokeWidth = 3.dp,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            is UIState.Error -> {
                Toast.makeText(context, R.string.Failed_to_fetch_news, Toast.LENGTH_SHORT).show()
            }

            is UIState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(top = 23.dp, start = 27.dp, end = 23.dp)
                ) {
                    items((news as UIState.Success<List<ArticleUi>>).data) { article ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(vertical = 8.dp)
                                .clickable {
                                  navController.navigate(DetailRoute(article))
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(0.7f)
                                    .fillMaxHeight()
                                    .padding(end = 12.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .wrapContentHeight()
                                        .clip(RoundedCornerShape(32.dp))
                                        .background(colorResource(R.color.lang_grey).copy(alpha = 0.6f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = article.source.name,
                                        modifier = Modifier.padding(horizontal = 17.dp),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black
                                    )
                                }

                                Text(
                                    text = article.title,
                                    modifier = Modifier.padding(top = 5.dp),
                                    maxLines = 2,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    lineHeight = 20.sp
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = article.publishedAt,
                                        modifier = Modifier.weight(1f),
                                        color = colorResource(R.color.tv_grey),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = article.author,
                                        color = Color.Black,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Image(
                                painter = rememberAsyncImagePainter(article.urlToImage),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(102.dp)
                                    .height(80.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        }
                    }
                }
            }


        }
    }


}

