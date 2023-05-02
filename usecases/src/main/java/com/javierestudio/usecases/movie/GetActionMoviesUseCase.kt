package com.javierestudio.usecases.movie

import com.javierestudio.data.repository.movie.MoviesRepository
import javax.inject.Inject

class GetActionMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    operator fun invoke() = moviesRepository.actionMovies
}
