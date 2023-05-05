package com.javierestudio.instaflixapp.data.database

import com.javierestudio.data.common.Constants.ACTION_GENRE_ID
import com.javierestudio.data.common.Constants.ANIMATION_GENRE_ID
import com.javierestudio.data.common.Constants.COMEDY_GENRE_ID
import com.javierestudio.data.common.Constants.DRAMA_GENRE_ID
import com.javierestudio.domain.ProgramGenre

fun Int.convertToProgramGenre(): ProgramGenre {
    return when (this) {
        ACTION_GENRE_ID -> ProgramGenre.ACTION
        COMEDY_GENRE_ID -> ProgramGenre.COMEDY
        ANIMATION_GENRE_ID -> ProgramGenre.ANIMATION
        DRAMA_GENRE_ID -> ProgramGenre.DRAMA
        else -> ProgramGenre.POPULAR
    }
}
