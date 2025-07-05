package com.shang.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.shang.model.MovieListBean

const val DEMO_URL = "https://fastly.picsum.photos/id/1020/400/300.jpg?hmac=tyq3V0QObhO4gvke1hMd7uZOQ2Sd5LwaQYB9zLBdi2w"

@Composable
fun MovieCard(
    data: MovieListBean.Result,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium) // 先裁切，這樣背景和邊框都會被裁切
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = MaterialTheme.shapes.medium,
            ),
    ) {
        MovieCover(model = data.posterPath)
    }
}

@Composable
fun MovieCover(model: Any) {
    AsyncImage(
        model = model, // 使用傳入的圖片路徑
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize() // 圖片拉滿父容器
            .aspectRatio(3f / 4f),
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
fun MovieTitle() {
}

@Composable
fun MovieReleaseTitle() {
}

@Composable
fun MovieRating() {
}

@Composable
fun MovieCollectButton() {
}

// @Preview()
// @Composable
// fun MovieCardPreview() {
//    MovieCard(
//        modifier = Modifier,
//
//    )
// }
