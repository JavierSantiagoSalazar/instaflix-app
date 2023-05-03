package com.javierestudio.usecases.detail

import com.javierestudio.data.repository.movie.MoviesRepository
import com.javierestudio.domain.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindMovieUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(id: Int): Flow<Movie> = repository.findById(id)
}
