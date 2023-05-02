package com.javierestudio.instaflixapp.data.database

import com.javierestudio.data.common.Constants

fun Int.convertToProgramType(): ProgramType {
    return when (this) {
        Constants.ACTION_GENRE_ID -> ProgramType.ACTION
        Constants.COMEDY_GENRE_ID -> ProgramType.COMEDY
        else -> ProgramType.POPULAR
    }
}
