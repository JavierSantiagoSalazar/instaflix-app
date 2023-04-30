package com.javierestudio.data.datasource

import arrow.core.Either
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<Movie>>
}
