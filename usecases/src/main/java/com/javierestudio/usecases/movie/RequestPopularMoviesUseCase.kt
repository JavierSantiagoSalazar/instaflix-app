package com.javierestudio.usecases.movie

import com.javierestudio.data.repository.movie.MoviesRepository
import com.javierestudio.domain.Error
import javax.inject.Inject

class RequestPopularMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(isRefreshing: Boolean): Error? {
        return moviesRepository.requestPopularMovies(isRefreshing)
    }
}
