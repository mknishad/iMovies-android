package com.mknishad.imovies.domain.usecases

import com.mknishad.imovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWishlistCountUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<Int> = repository.getWishlistCount()
}
