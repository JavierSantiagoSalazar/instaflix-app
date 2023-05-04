package com.javierestudio.instaflixapp.page

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers

fun clickInRecyclerItem(recyclerId: Int, itemPosition: Int) {
    Espresso.onView(ViewMatchers.withId(recyclerId))
        .perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(itemPosition,
                ViewActions.click()
            )
        )
}

fun verifyItemTextInRecyclerView(recyclerId: Int, text: String) {
    Espresso.onView(ViewMatchers.withId(recyclerId))
        .check(
            ViewAssertions.matches(
                ViewMatchers.hasDescendant(
                    ViewMatchers.withText(text)
                )
            )
        )
}

fun verifyTextInChild(parentId: Int, text: String) {
    Espresso.onView(ViewMatchers.withId(parentId))
        .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(text))))
}

fun verifyTextInView(viewId: Int, text: String) {
    Espresso.onView(ViewMatchers.withId(viewId))
        .check(ViewAssertions.matches(ViewMatchers.withText(text)))
}

fun checkIfViewIsDisplayed(viewId: Int) {
    Espresso.onView(ViewMatchers.withId(viewId))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}