package com.javierestudio.instaflixapp.data.database.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE :genreId IN (genreIds)")
    fun getMoviesByGenre(genreId: Int): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun findById(id: Int): Flow<Movie>

    @Query("SELECT COUNT(id) FROM Movie")
    suspend fun movieCount(): Int

    @Query("SELECT COUNT(id) FROM Movie WHERE genreIds = :genreId")
    suspend fun movieCountByGenreId(genreId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)
}