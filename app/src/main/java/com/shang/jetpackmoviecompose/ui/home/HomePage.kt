package com.shang.jetpackmoviecompose.ui.home


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage(homeViewModel: HomeViewModel = hiltViewModel()) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val data by homeViewModel.flow.collectAsState(initial = null)


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        ScrollableTabRow(
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color.Yellow,
                )
            },
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colorScheme.background
        ) {
            TabItem.tabs().forEachIndexed { index, tabItem ->
                Tab(
                    selectedContentColor = Color.Yellow,
                    unselectedContentColor = Color.Black,
                    selected = pagerState.currentPage == index, onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    }, text = {
                        Text(text = tabItem.title)
                    }
                )
            }

        }
        HorizontalPager(
            state = pagerState,
            pageCount = 10,
            modifier = Modifier.fillMaxSize(),

            ) {
            Text(text = "${data?.toString()}")

        }


    }
}

class TabItem(val title: String) {
    companion object {
        fun tabs(): MutableList<TabItem> {
            val list = mutableListOf<TabItem>()
            list.add(TabItem("111"))
            list.add(TabItem("222"))
            list.add(TabItem("333"))
            list.add(TabItem("333"))
            list.add(TabItem("333"))
            list.add(TabItem("333"))
            list.add(TabItem("333"))
            list.add(TabItem("333"))
            list.add(TabItem("333"))
            list.add(TabItem("333"))
            return list
        }
    }
}