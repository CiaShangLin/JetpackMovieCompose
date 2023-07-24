package com.shang.jetpackmoviecompose.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shang.jetpackmovie.bean.IBaseMovie
import java.io.Serializable

@Entity(tableName = "MovieFavorEntity")
data class MovieFavorEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val poster_path: String,
    val vote_average: Double,
    val release_date: String,
    val timestamp: Int
) : IBaseMovie {

    companion object {
        fun convert(data: IBaseMovie): MovieFavorEntity {
            return MovieFavorEntity(
                data.getMovieID(),
                data.getMovieTitle(),
                data.getPosterPath(),
                data.getVoteAverage(),
                data.getReleaseDate(),
                (System.currentTimeMillis()/1000).toInt()
            )
        }
    }

    override fun getMovieID(): Int = id

    override fun getMovieTitle(): String = title

    override fun getPosterPath(): String = poster_path

    override fun getVoteAverage(): Double = vote_average

    override fun getReleaseDate(): String = release_date
}