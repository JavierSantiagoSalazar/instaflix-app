package com.javierestudio.data.datasource.thshow

import com.javierestudio.domain.Error
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.domain.TvShow
import kotlinx.coroutines.flow.Flow

interface TvShowLocalDataSource {
    val tvShows: Flow<List<TvShow>>
    val animationTvShows: Flow<List<TvShow>>
    val dramaTvShows: Flow<List<TvShow>>

    suspend fun isEmpty(): Boolean
    suspend fun isTvShowsEmptyByGenreId(programGenre: ProgramGenre): Boolean

    fun findById(id: Int): Flow<TvShow>
    suspend fun save(tvShow: List<TvShow>, programGenre: ProgramGenre): Error?

    suspend fun deleteTvShowsByGenre(genre: ProgramGenre): Error?
}