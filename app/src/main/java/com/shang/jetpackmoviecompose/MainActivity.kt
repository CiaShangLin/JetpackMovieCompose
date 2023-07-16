package com.shang.jetpackmoviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shang.jetpackmoviecompose.ui.theme.JetpackMovieComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackMovieComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(20) {
                            BaseMovieViewHolder().MovieViewHolder()
                        }
                    }
//                    Column {
//                        BaseMovieViewHolder().MovieViewHolder()
//                        Spacer(modifier = Modifier.height(8.dp))
//                        HomeMovieViewHolder().MovieViewHolder()
//                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun ImageView() {
    val imageModifier = Modifier
        .size(300.dp)
        .border(BorderStroke(1.dp, Color.Black))
        .background(Color.Yellow)
    Image(
        painter = painterResource(id = R.drawable.icon_gmail),
        contentDescription = "",
        modifier = imageModifier
    )
}


@Composable
fun MovieViewHolder() {
    Card(
        modifier = Modifier.padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Box() {
            Column(modifier = Modifier.defaultMinSize()) {
                AsyncImage(
                    model = "https://i.imgur.com/Jc4gb9V.jpeg",
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(4f / 3f),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "蜘蛛人",
                    style = com.shang.jetpackmoviecompose.ui.theme.Typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "上映日:2023-7-16",
                        style = com.shang.jetpackmoviecompose.ui.theme.Typography.titleSmall
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icon_favor),
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

