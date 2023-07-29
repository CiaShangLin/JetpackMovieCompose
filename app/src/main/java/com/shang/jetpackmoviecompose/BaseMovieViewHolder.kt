package com.shang.jetpackmoviecompose

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.shang.jetpackmovie.bean.IBaseMovie
import com.shang.jetpackmoviecompose.ui.theme.Typography


@Composable
fun MovieViewHolder(data: IBaseMovie) {
    Card(
        modifier = Modifier.padding(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        ConstraintLayout(modifier = Modifier.defaultMinSize()) {
            val (cover, favor, title, day, ratio) = createRefs()
//            "https://i.imgur.com/Jc4gb9V.jpeg"
            //Configuration.getConfiguration()?.images?.base_url+original
            AsyncImage(
                model = "https://i.imgur.com/Jc4gb9V.jpeg",
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(3f / 4f)
                    .constrainAs(cover) {},
                contentScale = ContentScale.Crop
            )
            Ratio(ratio = data.getVoteAverage() * 10, Modifier
                .constrainAs(ratio) {
                    top.linkTo(cover.bottom, 0.dp)
                    bottom.linkTo(cover.bottom, 0.dp)
                    start.linkTo(cover.start, 3.dp)
                })
            Text(
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(cover.start, 4.dp)
                    top.linkTo(ratio.bottom, 4.dp)
                },
                text = data.getMovieTitle(),
                style = Typography.titleMedium,
                maxLines = 1
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .constrainAs(day) {
                        start.linkTo(cover.start, 4.dp)
                        top.linkTo(title.bottom, 4.dp)
                    },
                text = "上映日:${data.getReleaseDate()}",
                style = Typography.titleSmall,
                maxLines = 1
            )
            Favor(
                Modifier.constrainAs(favor) {
                    top.linkTo(cover.bottom, 4.dp)
                    end.linkTo(cover.end, 4.dp)
                }
            )
        }
    }
}

@Composable
fun Favor(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.icon_favor),
        contentDescription = null,
        modifier = modifier.size(25.dp)
    )
}


@Composable
fun Ratio(ratio: Double, modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(40.dp)
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            val arcRadius = size.minDimension / 2
            val arcCenter = Offset(size.width / 2, size.height / 2)
            val startAngle = -90f
            val sweepAngle = (ratio  / 100 * 360).toFloat()
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
        Text(text = "${ratio.toInt()}%", style = TextStyle(color = Color.White, fontSize = 10.sp))
    }
}

