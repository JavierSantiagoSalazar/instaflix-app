package com.javierestudio.usecases.movie

import com.javierestudio.data.repository.movie.MoviesRepository
import com.javierestudio.domain.Error
import com.javierestudio.domain.ProgramGenre
import javax.inject.Inject

class RequestMoviesByGenreIdUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(
        isRefreshing: Boolean,
        genreId: Int,
        programGenre: ProgramGenre,
    ): Error? {
        return moviesRepository.requestMovieByGenreId(isRefreshing, genreId, programGenre)
    }
}
