package com.shang.data.model

import com.shang.database.entity.MovieEntity
import com.shang.model.MovieBean

fun MovieBean.asEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        timestamp = timestamp,
    )
}
