package com.shang.jetpackmoviecompose.ui.home


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shang.jetpackmoviecompose.ui.genre.GenrePage
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage(homeViewModel: HomeViewModel = hiltViewModel()) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val data by homeViewModel.movieGenreFlow.collectAsState(initial = null)


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        ScrollableTabRow(
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color(0xFFFFD306)
                )
            },
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colorScheme.background
        ) {
            if (data?.genres.isNullOrEmpty()) {
                Spacer(modifier = Modifier.size(0.dp))
            } else {
                data?.genres?.forEachIndexed { index, genre ->
                    Tab(
                        selectedContentColor = Color(0xFFFFD306),
                        unselectedContentColor = Color.Black,
                        selected = pagerState.currentPage == index, onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        }, text = {
                            Text(text = genre.name)
                        }
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            pageCount = data?.genres?.size ?: 0,
            modifier = Modifier.fillMaxSize(),) {
            GenrePage(data!!.genres[it])
        }
    }
}