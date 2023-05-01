package com.javierestudio.usecases.tvshow

import com.javierestudio.data.repository.tvshow.TvShowRepository
import com.javierestudio.domain.Error
import javax.inject.Inject

class RequestPopularTvShowsUseCase @Inject constructor(private val moviesRepository: TvShowRepository) {

    suspend operator fun invoke(): Error? {
        return moviesRepository.requestPopularTvShows()
    }
}