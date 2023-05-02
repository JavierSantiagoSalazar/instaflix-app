package com.javierestudio.data.datasource.movie

import arrow.core.Either
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<Movie>>

    suspend fun findMoviesByGenre(genreId: Int): Either<Error, List<Movie>>
}
