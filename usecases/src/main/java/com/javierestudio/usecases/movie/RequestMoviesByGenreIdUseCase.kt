package com.javierestudio.usecases.movie

import com.javierestudio.data.repository.movie.MoviesRepository
import com.javierestudio.domain.Error
import javax.inject.Inject

class RequestMoviesByGenreIdUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(genreId: Int): Error? {
        return moviesRepository.requestMovieByGenreId(genreId)
    }
}
