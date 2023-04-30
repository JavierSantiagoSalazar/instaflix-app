package com.javierestudio.data.datasource

import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<Movie>>

    suspend fun isEmpty(): Boolean
    fun findById(id: Int): Flow<Movie>
    suspend fun save(movies: List<Movie>): Error?
}
