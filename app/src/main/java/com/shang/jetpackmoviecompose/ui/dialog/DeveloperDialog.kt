package com.shang.jetpackmoviecompose.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.shang.jetpackmoviecompose.R
import com.shang.jetpackmoviecompose.ui.common.HyperlinkText


@Composable
fun DeveloperDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.padding(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(16.dp))
                Developer()
                Spacer(modifier = Modifier.size(16.dp))
                Github()
                Spacer(modifier = Modifier.size(16.dp))
                Medium()
                Spacer(modifier = Modifier.size(16.dp))
                Gmail()
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun Developer() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "開發者 : 蔡尚霖",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
fun Github() {
    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.icon_github),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        HyperlinkText(
            fullText = "https://github.com/CiaShangLin",
            linkText = listOf("https://github.com/CiaShangLin"),
            hyperlinks = listOf("https://github.com/CiaShangLin"),
            linkTextColor = Color.Black,
        )

    }
}

@Composable
fun Medium() {
    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.icon_medium),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        HyperlinkText(
            fullText = "https://medium.com/@west7418",
            linkText = listOf("https://medium.com/@west7418"),
            hyperlinks = listOf("https://medium.com/@west7418"),
            linkTextColor = Color.Black,
        )
    }
}

@Composable
fun Gmail() {
    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.icon_gmail),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "west7418@gmail.com",
            color = Color.Black,
        )
    }
}