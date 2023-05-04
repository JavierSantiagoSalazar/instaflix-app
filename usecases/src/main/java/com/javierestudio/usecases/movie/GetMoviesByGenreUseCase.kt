package com.javierestudio.usecases.movie

import com.javierestudio.data.repository.movie.MoviesRepository
import com.javierestudio.domain.Movie
import com.javierestudio.domain.ProgramGenre
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    operator fun invoke(genre: ProgramGenre): Flow<List<Movie>> {
        return when (genre) {
            ProgramGenre.ACTION -> moviesRepository.actionMovies
            ProgramGenre.COMEDY -> moviesRepository.comedyMovies
            else -> moviesRepository.popularMovies
        }
    }
}
