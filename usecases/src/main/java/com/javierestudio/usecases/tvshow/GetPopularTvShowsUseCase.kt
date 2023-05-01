package com.javierestudio.usecases.tvshow

import com.javierestudio.data.repository.tvshow.TvShowRepository
import javax.inject.Inject

class GetPopularTvShowsUseCase @Inject constructor(private val repository: TvShowRepository) {

    operator fun invoke() = repository.popularTvShows
}