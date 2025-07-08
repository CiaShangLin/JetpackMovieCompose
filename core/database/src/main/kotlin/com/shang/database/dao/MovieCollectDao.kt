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
    fun getAllMovies(): Flow<List<MovieCollectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieCollect(entity: MovieCollectEntity)

    @Delete
    suspend fun deleteMovie(entity: MovieCollectEntity)

    /**
     * 取得所有已收藏電影的 id
     * - Flow 型別可即時監聽資料變化
     * - 僅查詢 id 欄位，效能最佳
     */
    @Query("SELECT id FROM MovieCollectEntity")
    fun collectedMovieIds(): Flow<List<Int>>

    /**
     * 透過 id 查詢單一電影收藏實體
     * 返回值為 nullable，若無資料則返回 null
     */
    @Query("SELECT * FROM MovieCollectEntity WHERE id = :id")
    fun getMovieCollectEntityById(id: Int): Flow<MovieCollectEntity?>
}
