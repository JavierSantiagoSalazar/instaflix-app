package com.javierestudio.instaflixapp.data.database.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javierestudio.instaflixapp.data.database.ProgramType
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE programType = :programType")
    fun getMoviesByGenreId(programType: ProgramType): Flow<List<Movie>>

    @Query("SELECT COUNT(id) FROM Movie")
    suspend fun movieCount(): Int

    @Query("SELECT COUNT(id) FROM Movie WHERE programType = :programType")
    suspend fun movieCountByProgramType(programType: ProgramType): Int

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun findById(id: Int): Flow<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)
}
