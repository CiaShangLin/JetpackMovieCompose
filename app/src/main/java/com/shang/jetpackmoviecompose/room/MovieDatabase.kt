package com.shang.jetpackmoviecompose.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shang.jetpackmoviecompose.room.dao.MovieFavorDao
import com.shang.jetpackmoviecompose.room.entity.MovieFavorEntity

@Database(entities = [MovieFavorEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "MovieDatabase"
    }

    abstract fun movieFavorDao(): MovieFavorDao
}