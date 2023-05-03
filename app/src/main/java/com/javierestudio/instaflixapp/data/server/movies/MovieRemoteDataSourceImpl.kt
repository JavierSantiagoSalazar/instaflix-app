package com.javierestudio.instaflixapp.data.server.movies

import arrow.core.Either
import com.javierestudio.data.datasource.movie.MovieRemoteDataSource
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.domain.ProgramType
import com.javierestudio.instaflixapp.data.database.convertToProgramGenre
import com.javierestudio.instaflixapp.data.tryCall
import com.javierestudio.instaflixapp.di.annotations.ApiKey
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    @ApiKey private val apiKey: String,
    private val movieRemoteService: MovieRemoteService,
) : MovieRemoteDataSource {

    override suspend fun findPopularMovies(region: String): Either<Error, List<Movie>> = tryCall {
        movieRemoteService
            .listPopularMovies(apiKey, region)
            .results
            .toDomainModel(ProgramGenre.POPULAR)
    }

    override suspend fun findMoviesByGenre(genreId: Int): Either<Error, List<Movie>> = tryCall {
        movieRemoteService
            .listMoviesByGenre(apiKey, genreId)
            .results
            .toDomainModel(genreId.convertToProgramGenre())
    }
}

private fun List<RemoteMovie>.toDomainModel(programGenre: ProgramGenre): List<Movie> =
    map { it.toDomainModel(programGenre) }

private fun RemoteMovie.toDomainModel(programGenre: ProgramGenre): Movie =
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
        genreIds = genreIds,
        voteAverage = voteAverage,
        programType = ProgramType.MOVIE,
        programGenre = programGenre
    )
