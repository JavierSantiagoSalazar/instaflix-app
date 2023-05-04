package com.javierestudio.usecases.tvshow

import com.javierestudio.data.repository.tvshow.TvShowRepository
import com.javierestudio.domain.Error
import com.javierestudio.domain.ProgramGenre
import javax.inject.Inject

class RequestTvShowsByGenreIdUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
    ) {

    suspend operator fun invoke(
        isRefreshing: Boolean,
        genreId: Int,
        programGenre: ProgramGenre,
    ): Error? {
        return tvShowRepository.requestTvShowByGenreId(isRefreshing, genreId, programGenre)
    }
}
