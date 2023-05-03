package com.javierestudio.instaflixapp.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.FragmentMovieBinding
import com.javierestudio.instaflixapp.ui.common.launchAndCollect
import com.javierestudio.instaflixapp.ui.common.setVisibleOrGone
import com.javierestudio.instaflixapp.ui.common.showErrorSnackBar
import com.javierestudio.instaflixapp.ui.common.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movie) {

    private val viewModel: MoviesViewModel by viewModels()

    private lateinit var movieState: MoviesState

    private val actionMoviesAdapter = MoviesAdapter { movieState.onMovieClicked(it) }
    private val comedyMoviesAdapter = MoviesAdapter { movieState.onMovieClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMovieBinding.bind(view).apply {
            recyclerActionMovies.adapter = actionMoviesAdapter
            recyclerComedyMovies.adapter = comedyMoviesAdapter
            swipeRefresh.setOnRefreshListener {
                viewModel.getPrograms(true)
                swipeRefresh.isRefreshing = false
            }
        }

        movieState = buildMoviesState().apply { setToolbarTitle() }

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.progress.setVisibleOrGone(it.loading)
            actionMoviesAdapter.submitList(it.actionMovies)
            comedyMoviesAdapter.submitList(it.comedyMovies)
            it.error?.let { error ->
                view.showErrorSnackBar(error) {
                    viewModel.getPrograms()
                }
            }
        }

        viewModel.getPrograms()
    }
}
