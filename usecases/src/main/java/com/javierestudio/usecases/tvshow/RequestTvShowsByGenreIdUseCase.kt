package com.javierestudio.usecases.tvshow

import com.javierestudio.data.repository.movie.MoviesRepository
import com.javierestudio.data.repository.tvshow.TvShowRepository
import com.javierestudio.domain.Error
import javax.inject.Inject

class RequestTvShowsByGenreIdUseCase @Inject constructor(private val tvShowRepository: TvShowRepository) {

    suspend operator fun invoke(genreId: Int): Error? {
        return tvShowRepository.requestMovieByGenreId(genreId)
    }
}
