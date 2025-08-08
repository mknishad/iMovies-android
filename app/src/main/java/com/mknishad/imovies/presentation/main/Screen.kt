package com.mknishad.imovies.presentation.main

import androidx.annotation.StringRes
import com.mknishad.imovies.R

/**
 * enum values that represent the screens in the app
 */
enum class Screen(@StringRes val title: Int) {
    Splash(title = R.string.app_name),
    MovieList(title = R.string.app_name),
    MovieDetails(title = R.string.movie_details),
    Wishlist(title = R.string.wish_list)
}
