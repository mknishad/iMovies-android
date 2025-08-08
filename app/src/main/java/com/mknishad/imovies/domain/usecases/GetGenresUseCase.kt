package com.mknishad.imovies.domain.usecases

import com.mknishad.imovies.domain.model.Genre
import com.mknishad.imovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<List<Genre>> = repository.getAllGenres()
}
