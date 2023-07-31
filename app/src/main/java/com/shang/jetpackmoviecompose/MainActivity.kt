package com.shang.jetpackmoviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.shang.jetpackmoviecompose.ui.favorites.FavoritesPage
import com.shang.jetpackmoviecompose.ui.genre.GenreViewModel
import com.shang.jetpackmoviecompose.ui.home.HomePage
import com.shang.jetpackmoviecompose.ui.setting.SettingPage
import com.shang.jetpackmoviecompose.ui.theme.JetpackMovieComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var factory: MainViewModel.Factory

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideMainViewModelFactory(factory, 1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackMovieComposeTheme {
                val navHostController = rememberNavController()
                Scaffold(
                    Modifier
                        .fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val items = listOf(
        BottomMenu.Home, BottomMenu.Favor, BottomMenu.Setting
    )
    var selectedItem by remember { mutableStateOf(0) }

    /**
     * 媽Ｂ的用material3,BottomNavigationItem的selectedContentColor會失效
     */
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                selectedContentColor = Color(0xFFFFD306),
                unselectedContentColor = Color.Black,
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


sealed class BottomMenu(val label: String, val icon: Int, val route: String) {
    object Home : BottomMenu("首頁", R.drawable.icon_home, "home")
    object Favor : BottomMenu("收藏", R.drawable.icon_favorite, "favorites")
    object Setting : BottomMenu("設定", R.drawable.icon_setting, "settings")
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