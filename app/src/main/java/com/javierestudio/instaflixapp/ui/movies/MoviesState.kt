package com.javierestudio.instaflixapp.ui.movies

import android.app.Activity
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.javierestudio.domain.Movie
import com.javierestudio.instaflixapp.R

fun Fragment.buildMoviesState(
    activity: Activity = requireActivity(),
    navController: NavController = findNavController(),
) = MoviesState(activity, navController)

class MoviesState(
    private val activity: Activity,
    private val navController: NavController,
) {

    fun onMovieClicked(movie: Movie) {
        val action = MoviesFragmentDirections.actionMovieToDetail(movie.programType, movie.id)
        navController.navigate(action)
    }

    fun setToolbarTitle() {
        activity.findViewById<TextView>(R.id.tvToolbarTitle).run {
            this.text = context.getString(R.string.tv_movie_toolbar_title)
        }
    }

}
