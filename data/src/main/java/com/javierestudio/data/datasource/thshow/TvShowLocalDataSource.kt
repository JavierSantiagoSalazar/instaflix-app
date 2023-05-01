package com.javierestudio.data.datasource.thshow

import com.javierestudio.domain.Error
import com.javierestudio.domain.TvShow
import kotlinx.coroutines.flow.Flow

interface TvShowLocalDataSource {
    val tvShows: Flow<List<TvShow>>

    suspend fun isEmpty(): Boolean
    fun findById(id: Int): Flow<TvShow>
    suspend fun save(tvShow: List<TvShow>): Error?
}