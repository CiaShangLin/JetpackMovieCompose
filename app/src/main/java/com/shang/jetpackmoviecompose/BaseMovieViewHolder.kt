package com.shang.jetpackmoviecompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun Ratio() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(50.dp)
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            val arcRadius = size.minDimension / 2
            val arcCenter = Offset(size.width / 2, size.height / 2)
            val startAngle = -90f
            val sweepAngle = 180f
            val useCenter = false // 是否连接起点和终点形成一个封闭区域
            val color = Color.Green
            val strokeWidth = 25.dp.value

            drawCircle(
                color = Color.Black,
                radius = size.minDimension
            )
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = useCenter,
                topLeft = arcCenter - Offset(arcRadius, arcRadius),
                size = Size(arcRadius * 2, arcRadius * 2),
                style = Stroke(width = strokeWidth),
            )
        }
        )
        Text(text = "50%", style = TextStyle(color = Color.White, fontSize = 14.sp))
    }
}

