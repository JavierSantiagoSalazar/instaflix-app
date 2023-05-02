package com.javierestudio.data.repository.tvshow

import com.javierestudio.data.common.Constants.POPULAR_GENRE_ID
import com.javierestudio.data.datasource.thshow.TvShowLocalDataSource
import com.javierestudio.data.datasource.thshow.TvShowRemoteDataSource
import com.javierestudio.domain.Error
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

    suspend fun requestPopularTvShows(): Error? {
        if (localDataSource.isEmpty()) {
            val tvShows = remoteDataSource.findPopularTvShows()
            tvShows.fold(ifLeft = { return it }) {
                localDataSource.save(it, POPULAR_GENRE_ID)
            }
        }
        return null
    }

    suspend fun requestMovieByGenreId(genreId: Int): Error? {
        if (localDataSource.isTvShowsEmptyByGenreId(genreId)) {
            val genreMovies = remoteDataSource.findTvShowsByGenre(genreId)
            genreMovies.fold(ifLeft = { return it }) {
                localDataSource.save(it, genreId)
            }
        }
        return null
    }

    suspend fun switchFavorite(tvShow: TvShow): Error? {
        val updatedTvShow = tvShow.copy(favorite = !tvShow.favorite)
        return localDataSource.save(listOf(updatedTvShow),5)
    }
}
