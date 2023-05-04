package com.javierestudio.instaflixapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierestudio.data.common.Constants.ACTION_GENRE_ID
import com.javierestudio.data.common.Constants.COMEDY_GENRE_ID
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.instaflixapp.data.toError
import com.javierestudio.usecases.movie.GetMoviesByGenreUseCase
import com.javierestudio.usecases.movie.RequestMoviesByGenreIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    private val requestMoviesByGenreIdUseCase: RequestMoviesByGenreIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getMoviesByGenreUseCase(ProgramGenre.ACTION)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { actionMovies -> _state.update { it.copy(actionMovies = actionMovies) } }
        }
        viewModelScope.launch {
            getMoviesByGenreUseCase(ProgramGenre.COMEDY)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { comedyMovies -> _state.update { it.copy(comedyMovies = comedyMovies) } }
        }
    }

    fun getPrograms(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val actionMoviesError = requestMoviesByGenreIdUseCase(
                isRefreshing = isRefreshing,
                genreId = ACTION_GENRE_ID,
                programGenre = ProgramGenre.ACTION
            )
            val comedyMoviesError = requestMoviesByGenreIdUseCase(
                isRefreshing = isRefreshing,
                genreId = COMEDY_GENRE_ID,
                programGenre = ProgramGenre.COMEDY
            )
            _state.value = _state.value.copy(loading = false, error = actionMoviesError)
            _state.value = _state.value.copy(loading = false, error = comedyMoviesError)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val actionMovies: List<Movie>? = null,
        val comedyMovies: List<Movie>? = null,
        val error: Error? = null
    )
}
