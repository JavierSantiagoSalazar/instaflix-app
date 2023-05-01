package com.javierestudio.usecases

import com.javierestudio.data.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke() = repository.popularMovies
}
