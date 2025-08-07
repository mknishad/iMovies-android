package com.mknishad.imovies.data.mappers

import com.mknishad.imovies.data.remote.dto.MovieDto
import com.mknishad.imovies.data.remote.dto.MovieResponseDto
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.model.MovieResponse

fun MovieResponseDto.toMovieResponse(): MovieResponse {
    return MovieResponse(
        genres = genres,
        movies = movies.map { it.toMovie() }
    )
}

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