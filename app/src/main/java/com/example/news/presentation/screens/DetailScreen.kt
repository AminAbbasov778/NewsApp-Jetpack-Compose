package com.example.news.presentation.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.news.R
import com.example.news.presentation.model.MenuAction
import com.example.news.presentation.model.news.ArticleUi
import com.example.news.presentation.utils.ShareUtils
import com.example.news.presentation.viewmodels.DetailViewModel

@Composable
fun DetailScreen(
    paddingValues: PaddingValues,
    navController: NavController, article: ArticleUi,
     viewModel: DetailViewModel = hiltViewModel(),
) {
    val isMenuVisible by viewModel.isMenuVisible
    val menuItems = listOf<MenuAction>(
        MenuAction.Save,MenuAction.Share
    )
    val isNewsSaved by viewModel.isNewsSaved
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.isNewsSaved(article)

        viewModel.shareEven.collect{
            ShareUtils.shareArticle(context,it.first,it.second)
        }
    }


    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberAsyncImagePainter(article.urlToImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(246.dp),
                contentScale = ContentScale.FillWidth
            )

            Column(
                modifier = Modifier
                    .padding(top = 191.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(color = Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 33.dp, top = 28.dp)
                        .wrapContentWidth()
                        .height(22.dp)
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
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(start = 33.dp, top = 24.dp, end = 34.dp)
                )
                Text(
                    text = article.author ,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.tv_blue),
                    modifier = Modifier
                        .padding(top = 18.dp, end = 34.dp)
                        .align(Alignment.End)
                )
                Text(
                    text = article.description,
                    modifier = Modifier.padding(start = 37.dp, top = 11.dp, end = 34.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = article.content ,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 33.dp, top = 16.dp, end = 34.dp)
                )
                Text(
                    text = "Read more...",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(end = 33.dp, top = 49.dp)
                        .align(Alignment.End).clickable{
                            val url = article.url
                            if (!url.isNullOrBlank() && url.startsWith("http")) {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                try {
                                    context.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast.makeText(context, "No browser found", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Invalid link", Toast.LENGTH_SHORT).show()
                            }
                        },
                    color = colorResource(R.color.second_tv__blue)
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 20.dp, start = 15.dp)
                    .width(119.dp)
                    .height(30.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(color = colorResource(R.color.lang_grey))
                    .clickable {
                        val canNavigateBack = navController.previousBackStackEntry != null
                        if (canNavigateBack) {
                            navController.popBackStack()
                        }

                    },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.padding(start = 12.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .width(20.dp)
                            .height(8.dp),
                        painter = painterResource(R.drawable.back),
                        contentDescription = null
                    )
                    Text(
                        text = article.title,
                        modifier = Modifier.padding(start = 10.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 22.dp, end = 17.dp)
                    .size(35.dp)
                    .clickable {
                        viewModel.changeMenuVisibility()
                    }
                    .background(color = colorResource(R.color.lang_grey), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                    modifier = Modifier
                )
            }
            if (isMenuVisible) {
                LazyColumn(
                    modifier = Modifier.padding(top = 57.dp, end = 33.dp)
                        .width(187.dp)
                        .background(
                            color = colorResource(R.color.light_white).copy(alpha = 0.95f),
                            shape = RoundedCornerShape(16.dp)
                        ).align(alignment = Alignment.TopEnd),
                ) {

                    items(menuItems) {

                        Row(
                            modifier = Modifier.padding(top = 19.dp,bottom = 11.dp)
                                .fillMaxWidth().clickable{
                                    viewModel.onClickMenuItem(it,article)
                                }, verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(if(isNewsSaved) it.icon.selectedIcon else it.icon.unselectedIcon),
                                modifier = Modifier
                                    .padding(start = 21.dp)
                                    .size(25.dp), tint = Color.Unspecified,
                                contentDescription = null
                            )

                            Text(
                                text = it.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 36.dp,)
                            )

                        }
                        if (menuItems.last() != it) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(color = colorResource(R.color.black))
                            )

                        }
                    }


                }
            }

        }
    }
}

