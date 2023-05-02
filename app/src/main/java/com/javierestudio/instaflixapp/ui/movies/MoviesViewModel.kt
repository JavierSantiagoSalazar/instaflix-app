package com.javierestudio.instaflixapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierestudio.data.common.Constants.ACTION_GENRE_ID
import com.javierestudio.data.common.Constants.COMEDY_GENRE_ID
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import com.javierestudio.instaflixapp.data.toError
import com.javierestudio.usecases.movie.GetActionMoviesUseCase
import com.javierestudio.usecases.movie.GetComedyMoviesUseCase
import com.javierestudio.usecases.movie.RequestMoviesByGenreIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    getActionMoviesUseCase: GetActionMoviesUseCase,
    getComedyMoviesUseCase: GetComedyMoviesUseCase,
    private val requestMoviesByGenreIdUseCase: RequestMoviesByGenreIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getActionMoviesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { actionMovies -> _state.update { it.copy(actionMovies = actionMovies) } }
        }
        viewModelScope.launch {
            getComedyMoviesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { comedyMovies -> _state.update { it.copy(comedyMovies = comedyMovies) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val actionMoviesError = requestMoviesByGenreIdUseCase(ACTION_GENRE_ID)
            val comedyMoviesError = requestMoviesByGenreIdUseCase(COMEDY_GENRE_ID)
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
