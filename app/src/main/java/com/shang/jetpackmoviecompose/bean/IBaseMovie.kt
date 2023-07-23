package com.shang.jetpackmovie.bean

interface IBaseMovie {
    fun getMovieID():Int
    fun getMovieTitle():String
    fun getPosterPath():String
    fun getVoteAverage():Double
    fun getReleaseDate():String
}