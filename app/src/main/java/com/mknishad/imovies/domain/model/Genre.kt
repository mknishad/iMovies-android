package com.mknishad.imovies.domain.model

data class Genre(
    val name: String
) {
    companion object {
        val ALL = Genre("All")
    }
}
