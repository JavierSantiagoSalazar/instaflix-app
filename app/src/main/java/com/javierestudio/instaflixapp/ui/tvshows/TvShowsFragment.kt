package com.javierestudio.instaflixapp.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.FragmentTvShowBinding
import com.javierestudio.instaflixapp.ui.common.launchAndCollect
import com.javierestudio.instaflixapp.ui.common.setVisibleOrGone
import com.javierestudio.instaflixapp.ui.common.showErrorSnackBar
import com.javierestudio.instaflixapp.ui.common.TvShowsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsFragment : Fragment(R.layout.fragment_tv_show) {

    private val viewModel: TvShowsViewModel by viewModels()

    private lateinit var tvShowState: TvShowsState

    private val animationTvShowsAdapter = TvShowsAdapter { tvShowState.onTvShowClicked(it) }
    private val dramaTvShowsAdapter = TvShowsAdapter { tvShowState.onTvShowClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTvShowBinding.bind(view).apply {
            recyclerAnimationTvShow.adapter = animationTvShowsAdapter
            recyclerDramaTvShow.adapter = dramaTvShowsAdapter
            swipeRefresh.setOnRefreshListener {
                viewModel.getPrograms(true)
                swipeRefresh.isRefreshing = false
            }
        }
        tvShowState = buildTvShowsFragment().apply { setToolbarTitle() }

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.progress.setVisibleOrGone(it.loading)
            animationTvShowsAdapter.submitList(it.animationTvShows)
            dramaTvShowsAdapter.submitList(it.dramaTvShows)
            it.error?.let { error ->
                view.showErrorSnackBar(error) {
                    viewModel.getPrograms()
                }
            }
        }

        viewModel.getPrograms()
    }
}
