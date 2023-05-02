package com.javierestudio.instaflixapp.ui.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierestudio.data.common.Constants.ANIMATION_GENRE_ID
import com.javierestudio.data.common.Constants.DRAMA_GENRE_ID
import com.javierestudio.domain.Error
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.data.toError
import com.javierestudio.usecases.tvshow.GetAnimationTvShowsUseCase
import com.javierestudio.usecases.tvshow.GetDramaTvShowsUseCase
import com.javierestudio.usecases.tvshow.RequestTvShowsByGenreIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    getAnimationTvShowsUseCase: GetAnimationTvShowsUseCase,
    getDramaTvShowsUseCase: GetDramaTvShowsUseCase,
    private val requestTvShowsByGenreIdUseCase: RequestTvShowsByGenreIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getAnimationTvShowsUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { animationTvShows -> _state.update { it.copy(animationTvShows = animationTvShows) } }
        }
        viewModelScope.launch {
            getDramaTvShowsUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { dramaTvShows -> _state.update { it.copy(dramaTvShows = dramaTvShows) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val animationTvShowsError = requestTvShowsByGenreIdUseCase(ANIMATION_GENRE_ID)
            val dramaTvShowsError = requestTvShowsByGenreIdUseCase(DRAMA_GENRE_ID)
            _state.value = _state.value.copy(loading = false, error = animationTvShowsError)
            _state.value = _state.value.copy(loading = false, error = dramaTvShowsError)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val animationTvShows: List<TvShow>? = null,
        val dramaTvShows: List<TvShow>? = null,
        val error: Error? = null,
    )
}
