package com.shang.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shang.database.entity.MovieCollectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieCollectDao {

    @Query("SELECT * FROM MovieCollectEntity")
    suspend fun getAllMovies(): List<MovieCollectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieCollectEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieCollectEntity)

    /**
     * 取得所有已收藏電影的 id
     * - Flow 型別可即時監聽資料變化
     * - 僅查詢 id 欄位，效能最佳
     */
    @Query("SELECT id FROM MovieCollectEntity")
    fun collectedMovieIds(): Flow<List<Int>>
}
