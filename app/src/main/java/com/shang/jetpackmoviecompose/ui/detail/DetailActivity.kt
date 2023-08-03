package com.shang.jetpackmoviecompose.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shang.jetpackmovie.bean.ActorBean
import com.shang.jetpackmovie.bean.Cast
import com.shang.jetpackmovie.bean.MovieListBean
import com.shang.jetpackmoviecompose.MovieViewHolder
import com.shang.jetpackmoviecompose.R
import com.shang.jetpackmoviecompose.ui.theme.JetpackMovieComposeTheme
import com.shang.jetpackmoviecompose.ui.theme.MyColorScheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"

        fun start(context: Context, movieId: Int) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_ID, movieId)
            context.startActivity(intent)
        }
    }

    private val mMovieId by lazy {
        intent.getIntExtra(MOVIE_ID, -1)
    }
    private val mViewModel: DetailViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel.setMovieId(movieId = mMovieId)

        setContent {
            JetpackMovieComposeTheme {

                val detailData = mViewModel.detailLiveData.observeAsState()
                val actorData = mViewModel.actorLiveData.observeAsState()
                val guessLike = mViewModel.guessLikeLiveData.observeAsState()

                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(modifier = Modifier

                    .background(MaterialTheme.colorScheme.background),
                   ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Content(detailData.value?.overview ?: "")
                        Spacer(modifier = Modifier.height(8.dp))
                        CastList(actorData.value)
                        Spacer(modifier = Modifier.height(8.dp))
                        GuessLike(guessLike.value)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

    @Composable
    fun Content(content: String) {
        Column() {
            Text(
                text = stringResource(id = R.string.summary),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 4.dp),
                color = MyColorScheme.textColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(4.dp),
                color = MyColorScheme.textColor
            )
        }
    }

    @Composable
    fun CastList(actorList: ActorBean?) {
        Column() {
            Text(
                text = stringResource(id = R.string.cast),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 4.dp),
                color = MyColorScheme.textColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(actorList?.cast?.size ?: 0) {
                    Actor(actorList?.cast!![it])
                }
            }
        }

    }

    @Composable
    fun Actor(data: Cast) {
        Card(
            modifier = Modifier
                .padding(0.dp)
                .width(138.dp),
            colors = CardDefaults.cardColors(
                containerColor = MyColorScheme.cardBackground,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
        ) {
            Column() {
                AsyncImage(
                    model = data.profile_path,
                    contentDescription = null,
                    modifier = Modifier.size(width = 138.dp, height = 175.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = data.name ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = MyColorScheme.textColor,
                    modifier = Modifier.padding(4.dp),
                    maxLines = 1,
                )
                Text(
                    text = data.character ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MyColorScheme.textColor,
                    modifier = Modifier.padding(4.dp),
                    maxLines = 1,
                )
            }
        }
    }

    @Composable
    fun GuessLike(data: MovieListBean?) {
        Column() {
            Text(
                text = stringResource(id = R.string.guess_like),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 4.dp),
                color = MyColorScheme.textColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(data?.results?.size ?: 0) {
                    MovieViewHolder(
                        data = data?.results!![it],
                        cardModifier = Modifier.width(width = 150.dp)
                    )
                }
            }
        }

    }
}