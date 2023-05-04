package com.javierestudio.instaflixapp.page

import androidx.test.espresso.Espresso

open class Page {
    companion object {
        inline fun <reified T : Page> on(): T {
            return Page().on()
        }
    }

    inline fun <reified T : Page> on(): T = T::class.constructors.first().call()


    fun back(): Page {
        Espresso.pressBack()
        return this
    }

    fun wait(seconds: Int): Page {
        Thread.sleep(seconds * 1000L)
        return this
    }
}
