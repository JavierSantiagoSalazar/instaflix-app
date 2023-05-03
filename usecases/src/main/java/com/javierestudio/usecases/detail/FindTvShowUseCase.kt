package com.javierestudio.usecases.detail

import com.javierestudio.data.repository.tvshow.TvShowRepository
import com.javierestudio.domain.TvShow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindTvShowUseCase @Inject constructor(private val repository: TvShowRepository) {

    operator fun invoke(id: Int): Flow<TvShow> = repository.findById(id)
}
