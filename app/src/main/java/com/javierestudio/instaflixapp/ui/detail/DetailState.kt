package com.javierestudio.instaflixapp.ui.detail

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.javierestudio.domain.Movie
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.FragmentDetailBinding
import com.javierestudio.instaflixapp.ui.common.format
import com.javierestudio.instaflixapp.ui.common.loadUrl
import com.javierestudio.instaflixapp.ui.common.setVisibleOrGone

fun Fragment.buildDetailState(activity: Activity = requireActivity()) = DetailState(activity)

class DetailState(private val activity: Activity) {

    fun setActivityComponents(visibility: Boolean) {
        activity.findViewById<Toolbar>(R.id.toolbar).setVisibleOrGone(visibility)
        activity.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setVisibleOrGone(visibility)
    }

    fun setDetailComponentsForMovie(binding: FragmentDetailBinding, movie: Movie) {
        with(binding) {
            programReleaseDate.text = movie.releaseDate
            programTitle.text = movie.title
            programDetailToolbar.title = movie.originalTitle
            programDetailSummary.text = movie.overview
            programVoteCount.text = movie.popularity.format()
            programVoteAverage.text = movie.voteAverage.toString()
            programDetailImage.loadUrl("https://image.tmdb.org/t/p/w185/${movie.backdropPath}")
        }
    }

    fun setDetailComponentsForTvShow(binding: FragmentDetailBinding, tvShow: TvShow) {
        with(binding) {
            programReleaseDate.text = tvShow.firstAirDate
            programTitle.text = tvShow.name
            programDetailToolbar.title = tvShow.originalName
            programDetailSummary.text = tvShow.overview
            programVoteCount.text = tvShow.popularity.format()
            programVoteAverage.text = tvShow.voteAverage.toString()
            programDetailImage.loadUrl("https://image.tmdb.org/t/p/w185/${tvShow.backdropPath}")
        }
    }
}
