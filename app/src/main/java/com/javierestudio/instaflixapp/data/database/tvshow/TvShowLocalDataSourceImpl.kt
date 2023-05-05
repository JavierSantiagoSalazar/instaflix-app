package com.javierestudio.instaflixapp.data.database.tvshow

import com.javierestudio.data.datasource.thshow.TvShowLocalDataSource
import com.javierestudio.domain.Error
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.domain.ProgramType
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.data.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.javierestudio.instaflixapp.data.database.tvshow.TvShow as DbTvShow

class TvShowLocalDataSourceImpl @Inject constructor(
    private val tvShowDao: TvShowDao,
) : TvShowLocalDataSource {

    override val tvShows: Flow<List<TvShow>> =
        tvShowDao.getTvShowsByGenreId(ProgramGenre.POPULAR)
            .map { it.toDomainModel(ProgramGenre.POPULAR) }

    override val animationTvShows: Flow<List<TvShow>> =
        tvShowDao.getTvShowsByGenreId(ProgramGenre.ANIMATION)
            .map { it.toDomainModel(ProgramGenre.ANIMATION) }

    override val dramaTvShows: Flow<List<TvShow>> =
        tvShowDao.getTvShowsByGenreId(ProgramGenre.DRAMA)
            .map { it.toDomainModel(ProgramGenre.DRAMA) }

    override suspend fun isEmpty(): Boolean = tvShowDao.tvShowCount() == 0
    override suspend fun isTvShowsEmptyByGenreId(programGenre: ProgramGenre): Boolean =
        tvShowDao.tvShowCountByProgramGenre(programGenre) == 0

    override fun findById(id: Int): Flow<TvShow> =
        tvShowDao.findById(id).map { it.toDomainModel(it.programGenre) }

    override suspend fun save(tvShow: List<TvShow>, programGenre: ProgramGenre): Error? = tryCall {
        tvShowDao.insertTvShows(tvShow.fromDomainModel(programGenre))
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )

    override suspend fun deleteTvShowsByGenre(genre: ProgramGenre): Error? =
        tryCall {
            tvShowDao.deleteTvShowsByGenre(genre)
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

}

private fun List<DbTvShow>.toDomainModel(programGenre: ProgramGenre): List<TvShow> =
    map { it.toDomainModel(programGenre) }

private fun DbTvShow.toDomainModel(programGenre: ProgramGenre): TvShow =
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
        programType = ProgramType.TV_SHOW,
        programGenre = programGenre
    )

private fun List<TvShow>.fromDomainModel(programGenre: ProgramGenre): List<DbTvShow> =
    map { it.fromDomainModel(programGenre) }

private fun TvShow.fromDomainModel(programGenre: ProgramGenre): DbTvShow = DbTvShow(
    name = name,
    overview = overview,
    firstAirDate = firstAirDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    originalLanguage = originalLanguage,
    originalName = originalName,
    popularity = popularity,
    voteAverage = voteAverage,
    programGenre = programGenre
)
