package com.javierestudio.data.datasource.thshow

import arrow.core.Either
import com.javierestudio.domain.Error
import com.javierestudio.domain.TvShow

interface TvShowRemoteDataSource {
    suspend fun findPopularTvShows(): Either<Error, List<TvShow>>

    suspend fun findTvShowsByGenre(genreId: Int): Either<Error, List<TvShow>>
}