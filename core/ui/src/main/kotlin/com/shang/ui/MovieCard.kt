package com.shang.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.request.ImageRequest
import com.shang.designsystem.component.JMAsyncImage

const val DEMO_URL =
    "https://fastly.picsum.photos/id/1020/400/300.jpg?hmac=tyq3V0QObhO4gvke1hMd7uZOQ2Sd5LwaQYB9zLBdi2w"

@Composable
fun MovieCard(
    data: MovieCardData,
    modifier: Modifier = Modifier,
    onMovieClick: (MovieCardData) -> Unit = {},
    onCollectClick: (MovieCardData) -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.medium,
            )
            .clip(MaterialTheme.shapes.medium)
            .background(
                color = MaterialTheme.colorScheme.surface,
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = MaterialTheme.shapes.medium,
            )
            .clickable {
                onMovieClick(data)
            },
    ) {
        Column() {
            Box {
                MovieCover(model = data.movieCardPosterPath)
                MovieRating(
                    modifier = Modifier
                        .padding(bottom = 8.dp, start = 8.dp)
                        .align(Alignment.BottomStart),
                    voteAverage = data.movieCardVoteAverage,
                )
                MovieCollectButton(
                    modifier = Modifier
                        .padding(top = 8.dp, end = 8.dp)
                        .align(Alignment.TopEnd),
                    isCollect = data.movieCardIsCollect,
                    onClick = {
                        onCollectClick(data)
                    },
                )
            }
            MovieTitle(data.movieCardTitle)
            MovieReleaseTitle(data.movieCardReleaseDate)
        }
    }
}

@Composable
fun MovieCover(model: Any) {
    JMAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(model)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize() // 圖片拉滿父容器
            .aspectRatio(3f / 4f)
            .padding(start = 1.dp, end = 1.dp, top = 1.dp),
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
fun MovieTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
        maxLines = 2,
    )
}

@Composable
fun MovieReleaseTitle(releaseDate: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.CalendarToday,
            contentDescription = "Release Date",
            modifier = Modifier
                .size(24.dp)
                .padding(start = 8.dp),
            tint = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = releaseDate,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 8.dp),
            maxLines = 1,
        )
    }
}

@Composable
fun MovieRating(modifier: Modifier, voteAverage: Double) {
    Row(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Rounded.Star,
            contentDescription = "Rating",
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            String.format("%.1f", voteAverage),
            modifier = Modifier.padding(end = 4.dp),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

@Composable
fun MovieCollectButton(modifier: Modifier, isCollect: Boolean, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(32.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small,
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = if (isCollect) {
                Icons.Rounded.Favorite
            } else {
                Icons.Rounded.FavoriteBorder
            },
            contentDescription = "Collect",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Preview()
@Composable
fun MovieCardPreview() {
    MovieCard(
        modifier = Modifier,
        data = MovieCardData(
            movieCardId = 1,
            movieCardTitle = "Sample Movie",
            movieCardPosterPath = DEMO_URL,
            movieCardReleaseDate = "2023-10-01",
            movieCardVoteAverage = 8.7,
            movieCardIsCollect = false,
            movieCardTimestamp = System.currentTimeMillis(),
        ),
    )
}
