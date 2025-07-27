package com.example.news.presentation.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.news.R
import com.example.news.presentation.model.DetailRoute
import com.example.news.presentation.model.LanguageRoute
import com.example.news.presentation.model.UIState
import com.example.news.presentation.model.news.ArticleUi
import com.example.news.presentation.viewmodels.HomeViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val news by viewModel.news
    val searchedResult by viewModel.searchedResult
    val query by viewModel.query
    val currentLanguage by viewModel.currentLanguage
    val currentDate by viewModel.currentDate

    LaunchedEffect(Unit) {
        viewModel.getLanguage()
        viewModel.getNews()
        viewModel.getCurrentDate()
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(Color.White)
    ) {
        HomeScreenHeader(
            navController = navController,
            currentLanguage = currentLanguage,
            currentDate = currentDate
        )
        HomeScreenBody(
            navController = navController,
            news = news
        )
        HomeScreenFooter(
            navController = navController,
            query = query,
            news = (news as? UIState.Success<List<ArticleUi>>)?.data ?: emptyList(),
            searchedResult = searchedResult,
            viewModel = viewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenHeader(
    navController: NavController,
    currentLanguage: String,
    currentDate: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 33.dp, start = 27.dp),
            verticalAlignment = Alignment.CenterVertically
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

        Row(
            modifier = Modifier.padding(start = 27.dp, top = 31.dp, end = 27.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentDate,
                color = colorResource(R.color.tv_grey),
                fontSize = 13.sp,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Medium
            )
            Box(
                modifier = Modifier
                    .width(75.dp)
                    .height(26.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = colorResource(R.color.lang_grey).copy(alpha = 0.6f))
                    .clickable {
                        navController.navigate(LanguageRoute)
                    },
            ) {
                Text(
                    text = currentLanguage,
                    modifier = Modifier.padding(start = 12.dp),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, top = 5.dp)
                        .size(16.dp)
                        .clip(shape = CircleShape)
                        .background(color = colorResource(R.color.main_tv_grey))
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(shape = CircleShape)
                            .background(color = colorResource(R.color.red))
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenBody(
    navController: NavController,
    news: UIState<List<ArticleUi>>,
) {
    val context = LocalContext.current

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
            LazyRow(
                modifier = Modifier
                    .padding(start = 27.dp, top = 41.dp)
                    .wrapContentWidth()
                    .height(180.dp)
            ) {
                items((news as UIState.Success<List<ArticleUi>>).data) { article ->
                    Box(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .width(320.dp)
                            .height(180.dp)
                            .background(color = Color.White)
                            .clickable {
                                navController.navigate(
                                    DetailRoute(
                                        article = article,
                                    )
                                )
                            }
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(article.urlToImage),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .padding(start = 18.dp, top = 18.dp)
                                .wrapContentWidth()
                                .height(22.dp)
                                .clip(RoundedCornerShape(32.dp))
                                .background(
                                    color = colorResource(R.color.main_tv_grey).copy(alpha = 0.5f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = article.author.toString(),
                                fontSize = 18.sp,
                                modifier = Modifier.padding(horizontal = 5.dp),
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(top = 86.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = article.title,
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .padding(top = 25.dp, start = 18.dp, end = 18.dp)
                                    .fillMaxSize()
                                    .wrapContentHeight()
                                    .clip(RoundedCornerShape(9.dp))
                                    .background(color = Color.White.copy(alpha = 0.5f)),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenFooter(
    navController: NavController,
    query: String,
    news: List<ArticleUi>,
    searchedResult: List<ArticleUi>,
    viewModel: HomeViewModel,
) {
    Box(
        modifier = Modifier
            .padding(top = 25.dp, start = 25.dp, end = 57.dp)
            .border(
                width = 1.5.dp,
                color = colorResource(R.color.et_grey),
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.Transparent, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 22.dp, vertical = 10.dp),
    ) {
        if (query.isEmpty()) {
            Text(
                text = "Search ...",
                fontSize = 11.sp,
                color = colorResource(R.color.et_hint_grey),
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
        BasicTextField(
            value = query,
            onValueChange = { viewModel.searchNews(it) },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 11.sp,
                color = Color.Black
            ),
            cursorBrush = SolidColor(Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
        )
    }

    val newsList = if (query.isEmpty()) news else searchedResult
    LazyColumn(
        modifier = Modifier.padding(top = 23.dp, start = 27.dp, end = 23.dp)
    ) {
        items(newsList) { article ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate(
                            DetailRoute(
                                article = article,
                            )
                        )
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