package com.javierestudio.instaflixapp.page

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*

fun clickInRecyclerItem(recyclerId: Int, itemPosition: Int) {
    onView(withId(recyclerId))
        .perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(itemPosition,
                click()
            )
        )
}

fun verifyItemTextInRecyclerView(recyclerId: Int, text: String) {
    onView(withId(recyclerId))
        .check(
            ViewAssertions.matches(
                hasDescendant(
                    withText(text)
                )
            )
        )
}

fun verifyTextInChild(parentId: Int, text: String) {
    onView(withId(parentId))
        .check(ViewAssertions.matches(hasDescendant(withText(text))))
}

fun verifyTextInView(viewId: Int, text: String) {
    onView(withId(viewId))
        .check(ViewAssertions.matches(withText(text)))
}

fun checkIfViewIsDisplayed(viewId: Int) {
    onView(withId(viewId))
        .check(ViewAssertions.matches(isDisplayed()))
}

fun clickBottomNavigationViewItem(buttonId: Int) {
    onView(withId(buttonId)).perform(click())
}
