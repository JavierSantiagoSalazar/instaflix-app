package com.javierestudio.instaflixapp.data.database.tvshow

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javierestudio.domain.ProgramGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM TvShow WHERE programGenre = :programGenre")
    fun getTvShowsByGenreId(programGenre: ProgramGenre): Flow<List<TvShow>>

    @Query("SELECT * FROM TvShow WHERE id = :id")
    fun findById(id: Int): Flow<TvShow>

    @Query("SELECT COUNT(id) FROM TvShow")
    suspend fun tvShowCount(): Int

    @Query("SELECT COUNT(id) FROM TvShow WHERE programGenre = :programGenre")
    suspend fun tvShowCountByProgramGenre(programGenre: ProgramGenre): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShow>)

    @Query("DELETE FROM TvShow WHERE programGenre = :programGenre")
    suspend fun deleteTvShowsByGenre(programGenre: ProgramGenre)
}
