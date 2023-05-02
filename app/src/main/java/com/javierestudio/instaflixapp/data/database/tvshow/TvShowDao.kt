package com.javierestudio.instaflixapp.data.database.tvshow

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javierestudio.instaflixapp.data.database.ProgramType
import com.javierestudio.instaflixapp.data.database.movie.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM TvShow")
    fun getAll(): Flow<List<TvShow>>

    @Query("SELECT * FROM TvShow WHERE id = :id")
    fun findById(id: Int): Flow<TvShow>

    @Query("SELECT * FROM TvShow WHERE programType = :programType")
    fun getTvShowsByGenreId(programType: ProgramType): Flow<List<TvShow>>

    @Query("SELECT COUNT(id) FROM TvShow")
    suspend fun tvShowCount(): Int

    @Query("SELECT COUNT(id) FROM TvShow WHERE programType = :programType")
    suspend fun tvShowCountByProgramType(programType: ProgramType): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShow>)
}
