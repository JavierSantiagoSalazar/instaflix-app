package com.javierestudio.data.repository.movie

import com.javierestudio.data.RegionRepository
import com.javierestudio.data.common.Constants.POPULAR_GENRE_ID
import com.javierestudio.data.datasource.movie.MovieLocalDataSource
import com.javierestudio.data.datasource.movie.MovieRemoteDataSource
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource,
) {
    val popularMovies get() = localDataSource.movies
    val actionMovies get() = localDataSource.actionMovies
    val comedyMovies get() = localDataSource.comedyMovies

    fun findById(id: Int): Flow<Movie> = localDataSource.findById(id)

    suspend fun requestPopularMovies(): Error? {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            movies.fold(ifLeft = { return it }) {
                localDataSource.save(it, POPULAR_GENRE_ID)
            }
        }
        return null
    }

    suspend fun requestMovieByGenreId(genreId: Int): Error? {
        if (localDataSource.isMoviesEmptyByGenreId(genreId)) {
            val genreMovies = remoteDataSource.findMoviesByGenre(genreId)
            genreMovies.fold(ifLeft = { return it }) {
                localDataSource.save(it, genreId)
            }
        }
        return null
    }

    suspend fun switchFavorite(movie: Movie): Error? {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        return localDataSource.save(listOf(updatedMovie), 5)
    }
}
