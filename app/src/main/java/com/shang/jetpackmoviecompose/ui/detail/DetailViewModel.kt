package com.shang.jetpackmoviecompose.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.skydoves.sandwich.getOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val mDetailRepository: DetailRepository) :
    ViewModel() {

    private var mMovieId = -1

    fun setMovieId(movieId: Int) {
        mMovieId = movieId
    }


    val detailLiveData = liveData {
        val movieDetail = mDetailRepository.getMovieDetail(mMovieId).getOrNull()
        emit(movieDetail)
    }

    val actorLiveData = liveData {
        val movieActor = mDetailRepository.getMovieActor(mMovieId).getOrNull()
        emit(movieActor)
    }

    val guessLikeLiveData = liveData {
        val movieRecommendations = mDetailRepository.getMovieRecommendations(mMovieId).getOrNull()
        emit(movieRecommendations)
    }
}