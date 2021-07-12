package com.bsamy.musix.domain.models.music

data class MetaDataDomainModel(
    val originalSongId: String,
    val originalTitle: String,
    val releaseYear: String,
    val genresHierarchy: List<String>,
    val moods: List<String>,
    val tempos: List<String>,
    val languages: String
)
