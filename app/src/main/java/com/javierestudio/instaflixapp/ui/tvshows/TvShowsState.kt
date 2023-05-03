package com.javierestudio.instaflixapp.ui.tvshows

import android.app.Activity
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.R

fun Fragment.buildTvShowsFragment(
    activity: Activity = requireActivity(),
    navController: NavController = findNavController(),
) = TvShowsState(activity, navController)

class TvShowsState(
    private val activity: Activity,
    private val navController: NavController,
) {

    fun onTvShowClicked(tvShow: TvShow) {
        val action = TvShowsFragmentDirections.actionShowToDetail(tvShow.programType, tvShow.id)
        navController.navigate(action)
    }

    fun setToolbarTitle() {
        activity.findViewById<TextView>(R.id.tvToolbarTitle).run {
            this.text = context.getString(R.string.tv_tv_show_toolbar_title)
        }
    }
}
