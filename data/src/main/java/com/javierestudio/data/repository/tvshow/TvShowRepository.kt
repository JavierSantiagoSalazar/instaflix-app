package com.javierestudio.data.repository.tvshow

import com.javierestudio.data.datasource.thshow.TvShowLocalDataSource
import com.javierestudio.data.datasource.thshow.TvShowRemoteDataSource
import com.javierestudio.domain.Error
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.domain.TvShow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvShowRepository @Inject constructor(
    private val localDataSource: TvShowLocalDataSource,
    private val remoteDataSource: TvShowRemoteDataSource,
) {
    val popularTvShows get() = localDataSource.tvShows
    val animationTvShows get() = localDataSource.animationTvShows
    val dramaTvShows get() = localDataSource.dramaTvShows

    fun findById(id: Int): Flow<TvShow> = localDataSource.findById(id)

    suspend fun requestPopularTvShows(isRefreshing: Boolean): Error? {
        if (localDataSource.isEmpty() || isRefreshing) {
            localDataSource.deleteTvShowsByGenre(ProgramGenre.POPULAR)
            val tvShows = remoteDataSource.findPopularTvShows()
            tvShows.fold(ifLeft = { return it }) {
                localDataSource.save(it, ProgramGenre.POPULAR)
            }
        }
        return null
    }

    suspend fun requestTvShowByGenreId(
        isRefreshing: Boolean,
        genreId: Int,
        programGenre: ProgramGenre,
    ): Error? {
        if (localDataSource.isTvShowsEmptyByGenreId(programGenre) || isRefreshing) {
            localDataSource.deleteTvShowsByGenre(programGenre)
            val genreMovies = remoteDataSource.findTvShowsByGenre(genreId)
            genreMovies.fold(ifLeft = { return it }) {
                localDataSource.save(it, programGenre)
            }
        }
        return null
    }
}
