package com.javierestudio.usecases.tvshow

import com.javierestudio.data.repository.tvshow.TvShowRepository
import javax.inject.Inject

class GetAnimationTvShowsUseCase @Inject constructor(private val tvShowRepository: TvShowRepository) {

    operator fun invoke() = tvShowRepository.animationTvShows
}
