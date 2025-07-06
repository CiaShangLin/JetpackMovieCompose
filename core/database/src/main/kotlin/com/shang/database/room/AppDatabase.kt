package com.shang.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shang.database.dao.MovieCollectDao
import com.shang.database.dao.MovieHistoryDao
import com.shang.database.entity.MovieCollectEntity
import com.shang.database.entity.MovieHistoryEntity

@Database(
    entities = [
        MovieCollectEntity::class,
        MovieHistoryEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME: String = "JetpackMovieComposeDatabase"
    }

    abstract fun createMovieCollectDao(): MovieCollectDao
    abstract fun createMovieHistoryDao(): MovieHistoryDao
}
