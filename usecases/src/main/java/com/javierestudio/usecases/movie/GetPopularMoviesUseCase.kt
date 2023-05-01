package com.javierestudio.usecases.movie

import com.javierestudio.data.repository.movie.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke() = repository.popularMovies
}