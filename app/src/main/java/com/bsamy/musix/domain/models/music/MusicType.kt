package com.bsamy.musix.domain.models.music

import com.bsamy.musix.utils.AppConstants

enum class MusicType(val key: String) {
    SONG("song"),
    RELEASE("release"),
    ARTIST("artist"),
    UNKNOWN(AppConstants.NOT_FOUND.toString())
}

fun String?.getMusicType(): MusicType {
    MusicType.values().forEach { type ->
        if (type.key == this) return type
    }
    return MusicType.UNKNOWN
}