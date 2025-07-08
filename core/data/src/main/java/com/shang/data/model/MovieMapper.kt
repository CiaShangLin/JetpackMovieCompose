package com.shang.data.model

import com.shang.database.entity.MovieCollectEntity
import com.shang.model.MovieListBean

fun MovieListBean.Result.asEntity(): MovieCollectEntity {
    return MovieCollectEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        timestamp = timestamp,
    )
}
