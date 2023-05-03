package com.javierestudio.instaflixapp.data.database.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javierestudio.domain.ProgramGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie WHERE programGenre = :programGenre")
    fun getMoviesByGenreId(programGenre: ProgramGenre): Flow<List<Movie>>

    @Query("SELECT COUNT(id) FROM Movie")
    suspend fun movieCount(): Int

    @Query("SELECT COUNT(id) FROM Movie WHERE programGenre = :programGenre")
    suspend fun movieCountByProgramGenre(programGenre: ProgramGenre): Int

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun findById(id: Int): Flow<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM Movie WHERE programGenre = :programGenre")
    suspend fun deleteMoviesByGenre(programGenre: ProgramGenre)
}
