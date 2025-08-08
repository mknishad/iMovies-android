package com.mknishad.imovies.domain.usecases

import com.mknishad.imovies.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieCountUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(): Int = repository.getMovieCount()
}
