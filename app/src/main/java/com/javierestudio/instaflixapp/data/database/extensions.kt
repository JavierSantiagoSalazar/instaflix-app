package com.javierestudio.instaflixapp.data.database

import com.javierestudio.data.common.Constants

fun Int.convertToProgramType(): ProgramType {
    return when (this) {
        Constants.ACTION_GENRE_ID -> ProgramType.ACTION
        Constants.COMEDY_GENRE_ID -> ProgramType.COMEDY
        Constants.ANIMATION_GENRE_ID -> ProgramType.ANIMATION
        Constants.DRAMA_GENRE_ID -> ProgramType.DRAMA
        else -> ProgramType.POPULAR
    }
}
