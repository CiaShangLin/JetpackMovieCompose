package com.shang.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieHistoryEntity")
class MovieHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Int,
)

// fun MovieHistoryEntity.asExtendedModel(): Mo {
//    return MovieBean(
//        id = id,
//        title = title,
//        posterPath = posterPath,
//        voteAverage = voteAverage,
//        releaseDate = releaseDate,
//        timestamp = timestamp,
//    )
// }
