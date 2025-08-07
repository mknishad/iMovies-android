package com.mknishad.imovies.domain.usecases

import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesFromDatabaseUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.getMoviesFromDatabase()
}
