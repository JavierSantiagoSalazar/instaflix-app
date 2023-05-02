package com.javierestudio.instaflixapp.data.server.tvshows

import arrow.core.Either
import com.javierestudio.data.datasource.thshow.TvShowRemoteDataSource
import com.javierestudio.domain.Error
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.data.tryCall
import com.javierestudio.instaflixapp.di.ApiKey
import javax.inject.Inject

class TvShowRemoteDataSourceImpl @Inject constructor(
    @ApiKey private val apiKey: String,
    private val tvShowRemoteService: TvShowRemoteService,
) : TvShowRemoteDataSource {

    override suspend fun findPopularTvShows(): Either<Error, List<TvShow>> = tryCall {
        tvShowRemoteService
            .listPopularTvShows(apiKey)
            .results
            .toDomainModel()
    }

    override suspend fun findTvShowsByGenre(genreId: Int): Either<Error, List<TvShow>> = tryCall {
        tvShowRemoteService
            .listTvShowsByGenre(apiKey, genreId)
            .results
            .toDomainModel()
    }
}

private fun List<RemoteTvShow>.toDomainModel(): List<TvShow> = map { it.toDomainModel() }

private fun RemoteTvShow.toDomainModel(): TvShow =
    TvShow(
        id = id,
        name = name,
        overview = overview,
        firstAirDate = firstAirDate,
        posterPath = "https://image.tmdb.org/t/p/w185/$posterPath",
        backdropPath = backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" } ?: "",
        originalLanguage = originalLanguage,
        originalName = originalName,
        popularity = popularity,
        voteAverage = voteAverage,
        favorite = false
    )