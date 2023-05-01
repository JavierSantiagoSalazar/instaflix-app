package com.javierestudio.instaflixapp.data.database.tvshow

import com.javierestudio.data.datasource.thshow.TvShowLocalDataSource
import com.javierestudio.domain.Error
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.data.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.javierestudio.instaflixapp.data.database.tvshow.TvShow as DbTvShow

class TvShowLocalDataSourceImpl @Inject constructor(
    private val tvShowDao: TvShowDao
) : TvShowLocalDataSource {

    override val tvShows: Flow<List<TvShow>> = tvShowDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = tvShowDao.tvShowCount() == 0

    override fun findById(id: Int): Flow<TvShow> = tvShowDao.findById(id).map { it.toDomainModel() }

    override suspend fun save(tvShow: List<TvShow>): Error? = tryCall {
        tvShowDao.insertTvShows(tvShow.map { it.fromDomainModel() })
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun List<DbTvShow>.toDomainModel(): List<TvShow> = map { it.toDomainModel() }

private fun DbTvShow.toDomainModel(): TvShow =
    TvShow(
        id = id,
        name = name,
        overview = overview,
        firstAirDate = firstAirDate,
        posterPath = posterPath,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        originalName = originalName,
        popularity = popularity,
        voteAverage = voteAverage,
        favorite = favorite
    )

private fun List<TvShow>.fromDomainModel(): List<DbTvShow> = map { it.fromDomainModel() }

private fun TvShow.fromDomainModel(): DbTvShow = DbTvShow(
    id = id,
    name = name,
    overview = overview,
    firstAirDate = firstAirDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    originalLanguage = originalLanguage,
    originalName = originalName,
    popularity = popularity,
    voteAverage = voteAverage,
    favorite = favorite
)
