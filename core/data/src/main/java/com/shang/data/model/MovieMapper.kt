package com.shang.data.model

import com.shang.database.entity.MovieCollectEntity
import com.shang.database.entity.MovieHistoryEntity
import com.shang.model.MovieCardResult

/**
 *
 */
fun MovieCardResult.asCollectEntity(): MovieCollectEntity {
    return MovieCollectEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        timestamp = timestamp,
    )
}

/**
 *
 */
fun MovieCardResult.asHistoryEntity(): MovieHistoryEntity {
    return MovieHistoryEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        timestamp = timestamp,
    )
}
