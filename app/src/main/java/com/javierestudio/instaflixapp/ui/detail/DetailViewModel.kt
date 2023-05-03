package com.javierestudio.instaflixapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import com.javierestudio.domain.ProgramType
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.data.toError
import com.javierestudio.instaflixapp.di.annotations.ProgramId
import com.javierestudio.usecases.detail.FindMovieUseCase
import com.javierestudio.usecases.detail.FindTvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.javierestudio.instaflixapp.di.annotations.ProgramType as ProgramTypeAnnotation


@HiltViewModel
class DetailViewModel @Inject constructor(
    @ProgramId private val programId: Int,
    @ProgramTypeAnnotation private val programType: ProgramType,
    findMovieUseCase: FindMovieUseCase,
    findTvShowUseCase: FindTvShowUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            if (programType == ProgramType.MOVIE) {
                findMovieUseCase(programId)
                    .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                    .collect { movie -> _state.update { UiState(movie = movie) } }
            } else {
                findTvShowUseCase(programId)
                    .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                    .collect { tvShow -> _state.update { UiState(tvShow = tvShow) } }
            }

        }
    }

    data class UiState(
        val movie: Movie? = null,
        val tvShow: TvShow? = null,
        val error: Error? = null
    )
}
