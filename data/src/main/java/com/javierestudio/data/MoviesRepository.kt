package com.javierestudio.data

import com.javierestudio.data.datasource.MovieLocalDataSource
import com.javierestudio.data.datasource.MovieRemoteDataSource
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository@Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) {
    val popularMovies get() = localDataSource.movies

    fun findById(id: Int): Flow<Movie> = localDataSource.findById(id)

    suspend fun requestPopularMovies(): Error? {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            movies.fold(ifLeft = { return it }) {
                localDataSource.save(it)
            }
        }
        return null
    }

    suspend fun switchFavorite(movie: Movie): Error? {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        return localDataSource.save(listOf(updatedMovie))
    }
}
