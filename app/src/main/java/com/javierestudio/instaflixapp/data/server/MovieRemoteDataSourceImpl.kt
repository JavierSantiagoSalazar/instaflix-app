package com.javierestudio.instaflixapp.data.server

import arrow.core.Either
import com.javierestudio.data.datasource.MovieRemoteDataSource
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import com.javierestudio.instaflixapp.data.tryCall
import com.javierestudio.instaflixapp.di.ApiKey
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    @ApiKey private val apiKey: String,
    private val remoteService: RemoteService
): MovieRemoteDataSource {
    override suspend fun findPopularMovies(region: String): Either<Error, List<Movie>> = tryCall {
        remoteService
            .listPopularMovies(apiKey, region)
            .results
            .toDomainModel()
    }

}

private fun List<RemoteMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun RemoteMovie.toDomainModel(): Movie =
    Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = "https://image.tmdb.org/t/p/w185/$posterPath",
        backdropPath = backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" } ?: "",
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        popularity = popularity,
        voteAverage = voteAverage,
        favorite = false
    )
