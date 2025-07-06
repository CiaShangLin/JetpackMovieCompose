package com.shang.data.model

import com.shang.database.entity.MovieCollectEntity
import com.shang.model.MovieBean

fun MovieBean.asEntity(): MovieCollectEntity {
    return MovieCollectEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        timestamp = timestamp,
    )
}
