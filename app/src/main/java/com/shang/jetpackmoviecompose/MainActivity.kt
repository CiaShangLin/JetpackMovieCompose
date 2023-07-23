package com.shang.jetpackmoviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.shang.jetpackmoviecompose.ui.theme.JetpackMovieComposeTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackMovieComposeTheme {
                val navHostController = rememberNavController()
                Scaffold(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    bottomBar = {
                        MyBottomNavigation(navHostController)
                    },

                ) {

                    Navigation(navHostController)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePage()
        }
        composable("favorites") {
            FavoritesPage()
        }
        composable("settings") {
            SettingPage()
        }
    }
}

@Composable
fun MyBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomMenu.Home, BottomMenu.Favor, BottomMenu.Setting
    )
    var selectedItem by remember { mutableStateOf(0) }
    BottomNavigation() {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    Icon(ImageVector.vectorResource(id = item.icon), contentDescription = null)
                },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = {
                    navController.navigate(item.route)
                    selectedItem = index
                })
        }
    }
}

@Composable
fun ViewHolder() {
    ConstraintLayout() {
        val (cover, favor) = createRefs()
        AsyncImage(
            model = "https://i.imgur.com/Jc4gb9V.jpeg",
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(4f / 3f)
                .constrainAs(cover) {

                },
            contentScale = ContentScale.Crop
        )
        Image(painter = painterResource(id = R.drawable.icon_favor),
            contentDescription = null,
            modifier = Modifier
                .size(25.dp)
                .constrainAs(favor) {
                    top.linkTo(cover.bottom, 0.dp)
                    bottom.linkTo(cover.bottom, 0.dp)
                })
    }
}


sealed class BottomMenu(val label: String, val icon: Int,val route:String) {
    object Home : BottomMenu("首頁", R.drawable.icon_home,"home")
    object Favor : BottomMenu("收藏", R.drawable.icon_favorite,"favorites")
    object Setting : BottomMenu("設定", R.drawable.icon_setting,"settings")
}

//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(2),
//                        verticalArrangement = Arrangement.spacedBy(8.dp),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp),
//                        contentPadding = PaddingValues(horizontal = 8.dp)
//                    ) {
//                        items(20) {
//                            BaseMovieViewHolder().MovieViewHolder()
//                        }
//                    }


//                    Column {
//                        BaseMovieViewHolder().MovieViewHolder()
//                        Spacer(modifier = Modifier.height(8.dp))
//                        HomeMovieViewHolder().MovieViewHolder()
//                    }