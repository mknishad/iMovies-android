package com.mknishad.imovies.data.repository

import com.mknishad.imovies.data.local.MovieDao
import com.mknishad.imovies.data.mappers.toMovie
import com.mknishad.imovies.data.mappers.toMovieEntity
import com.mknishad.imovies.data.remote.MovieApi
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val dao: MovieDao
) : MovieRepository {
    override suspend fun getMoviesFromNetwork(): List<Movie> {
        val movies = api.getMovies().movies
        dao.insertMovies(movies.map { it.toMovieEntity() })
        return movies.map { it.toMovie() }
    }

    override fun getMoviesFromDatabase(): Flow<List<Movie>> {
        return dao.getAllMovies().map { entities -> entities.map { it.toMovie() } }
    }
}
