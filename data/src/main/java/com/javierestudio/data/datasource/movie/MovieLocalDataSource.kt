package com.javierestudio.data.datasource.movie

import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import com.javierestudio.domain.ProgramGenre
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<Movie>>
    val actionMovies: Flow<List<Movie>>
    val comedyMovies: Flow<List<Movie>>

    suspend fun isEmpty(): Boolean
    suspend fun isMoviesEmptyByGenreId(genreId: Int): Boolean

    fun findById(id: Int): Flow<Movie>

    suspend fun save(movies: List<Movie>, genreId: Int): Error?

    suspend fun deleteMoviesByGenre(genre: ProgramGenre): Error?
}