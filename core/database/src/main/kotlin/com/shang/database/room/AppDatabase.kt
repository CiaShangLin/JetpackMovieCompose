package com.shang.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shang.database.dao.MovieDao
import com.shang.database.entity.MovieEntity

@Database(
    entities = [
        MovieEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME: String = "JetpackMovieComposeDatabase"
    }

    abstract fun createMovieDao(): MovieDao
}
