package com.javierestudio.usecases.tvshow

import com.javierestudio.data.repository.tvshow.TvShowRepository
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.domain.TvShow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvShowByGenreUseCase @Inject constructor(private val repository: TvShowRepository) {

    operator fun invoke(genre: ProgramGenre): Flow<List<TvShow>> {
        return when (genre) {
            ProgramGenre.ANIMATION -> repository.animationTvShows
            ProgramGenre.DRAMA -> repository.dramaTvShows
            else -> repository.popularTvShows
        }
    }
}
