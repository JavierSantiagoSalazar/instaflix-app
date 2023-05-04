package com.javierestudio.instaflixapp.ui.home

import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.page.*

class HomePage : Page() {

    fun clickInProgramRecyclerItem(recyclerId: Int, itemPosition: Int): HomePage {
        clickInRecyclerItem(recyclerId, itemPosition)
        return this
    }

    fun verifyToolbarTitle(title: String): HomePage {
        verifyTextInChild(R.id.programDetailToolbar, title)
        return this
    }

    fun verifyTitle(title: String): HomePage {
        verifyTextInView(R.id.programTitle, title)
        return this
    }

    fun verifyReleaseDateTitle(releaseDate: String): HomePage {
        verifyTextInView(R.id.programReleaseDate, releaseDate)
        return this
    }

    fun verifyVoteCount(voteCount: String): HomePage {
        verifyTextInView(R.id.programVoteCount, voteCount)
        return this
    }

    fun verifyVoteAverage(voteAverage: String): HomePage {
        verifyTextInView(R.id.programVoteAverage, voteAverage)
        return this
    }

    fun verifyOverview(overview: String): HomePage {
        verifyTextInView(R.id.programDetailSummary, overview)
        return this
    }

    fun checkIfToolBarIsDisplayed(): HomePage{
        checkIfViewIsDisplayed(R.id.programDetailToolbar)
        return this
    }

    fun verifyProgramTitleInRecycler(recyclerId: Int, title: String): HomePage {
        verifyItemTextInRecyclerView(recyclerId, title)
        return this
    }

    fun verifyActivityToolbarTitle(text: String) {
        verifyTextInChild(R.id.toolbar, text)
    }
}
