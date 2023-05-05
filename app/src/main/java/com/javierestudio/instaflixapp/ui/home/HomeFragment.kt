package com.javierestudio.instaflixapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.FragmentHomeBinding
import com.javierestudio.instaflixapp.ui.common.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var homeState: HomeState

    private val moviesAdapter = MoviesAdapter { homeState.onMovieClicked(it) }
    private val tvShowsAdapter = TvShowsAdapter { homeState.onTvShowClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view).apply {
            recyclerMovies.adapter = moviesAdapter
            recyclerSeries.adapter = tvShowsAdapter
            swipeRefresh.setOnRefreshListener {
                viewModel.checkInternetConnection{
                    viewModel.getPrograms(true)
                    swipeRefresh.isRefreshing = false
                }
                swipeRefresh.isRefreshing = false
            }
        }

        homeState = buildHomeState().apply { setToolbarTitle() }

        with(viewModel.state) {
            diff(this, { it.loading }) {
                it.let { binding.progress.setVisibleOrGone(it) }
            }
            diff(this, { it.movies }) { moviesAdapter.submitList(it) }
            diff(this, { it.tvShows }) { tvShowsAdapter.submitList(it) }
            launchAndCollect(this) {
                it.error?.let { error ->
                    if (view.isShown) {
                        view.showErrorSnackBar(error) {
                            viewModel.getPrograms()
                        }
                    }
                }
            }
        }

        homeState.requestLocationPermission {
            viewModel.getPrograms()
        }
    }
}
