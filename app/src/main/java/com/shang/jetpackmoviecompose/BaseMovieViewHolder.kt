package com.shang.jetpackmoviecompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shang.jetpackmoviecompose.ui.theme.Typography

open class BaseMovieViewHolder {

    @Composable
    open fun MovieViewHolder() {
        Card(
            modifier = Modifier.padding(0.dp),
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
                        style = Typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "上映日:2023-7-16",
                            style = Typography.titleSmall
                        )
                        Favor()
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }

    @Composable
    open fun Favor(){
        Image(
            painter = painterResource(id = R.drawable.icon_favor),
            contentDescription = null,
            modifier = Modifier.size(15.dp)
        )
    }

}

class HomeMovieViewHolder:BaseMovieViewHolder(){
    @Composable
    override fun Favor() {
        Image(
            painter = painterResource(id = R.drawable.icon_gmail),
            contentDescription = null,
            modifier = Modifier.size(15.dp)
        )
    }
}