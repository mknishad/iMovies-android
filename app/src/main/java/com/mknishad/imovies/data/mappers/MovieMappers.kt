package com.mknishad.imovies.data.mappers

import com.mknishad.imovies.data.local.entity.MovieEntity
import com.mknishad.imovies.data.remote.dto.MovieDto
import com.mknishad.imovies.domain.model.Movie

fun MovieDto.toMovie(): Movie {
    return Movie(
        actors = actors,
        director = director,
        genres = genres,
        id = id,
        plot = plot,
        posterUrl = posterUrl,
        runtime = runtime,
        title = title,
        year = year
    )
}

fun MovieDto.toMovieEntity(): MovieEntity {
    return MovieEntity(
        actors = actors,
        director = director,
        genres = genres,
        id = id,
        plot = plot,
        posterUrl = posterUrl,
        runtime = runtime,
        title = title,
        year = year,
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        actors = actors,
        director = director,
        genres = genres,
        id = id,
        plot = plot,
        posterUrl = posterUrl,
        runtime = runtime,
        title = title,
        year = year,
        isFavorite = isFavorite
    )
}
