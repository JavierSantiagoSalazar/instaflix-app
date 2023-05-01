package com.javierestudio.data.repository.tvshow

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

    fun findById(id: Int): Flow<TvShow> = localDataSource.findById(id)

    suspend fun requestPopularTvShows(): Error? {
        if (localDataSource.isEmpty()) {
            val tvShows = remoteDataSource.findPopularTvShows()
            tvShows.fold(ifLeft = { return it }) {
                localDataSource.save(it)
            }
        }
        return null
    }

    suspend fun switchFavorite(tvShow: TvShow): Error? {
        val updatedTvShow = tvShow.copy(favorite = !tvShow.favorite)
        return localDataSource.save(listOf(updatedTvShow))
    }
}
