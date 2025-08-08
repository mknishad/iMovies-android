package com.mknishad.imovies.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mknishad.imovies.presentation.moviedetails.MovieDetailsScreen
import com.mknishad.imovies.presentation.movielist.MovieListScreen
import com.mknishad.imovies.presentation.splash.SplashScreen
import com.mknishad.imovies.presentation.wishlist.WishlistScreen


@Composable
fun IMoviesApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.name,
        modifier = Modifier
            .fillMaxSize()
    ) {
        composable(route = Screen.Splash.name) {
            SplashScreen(
                onNavigateForward = {
                    navController.navigate(Screen.MovieList.name) {
                        popUpTo(Screen.Splash.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screen.MovieList.name) {
            MovieListScreen(
                onWishlistClick = {
                    navController.navigate(Screen.Wishlist.name)
                },
                onMovieClick = { movie ->
                    navController.navigate(Screen.MovieDetails.name + "?movieId=${movie.id}")
                }
            )
        }
        composable(route = Screen.Wishlist.name) {
            WishlistScreen(
                onMovieClick = { movie ->
                    navController.navigate(Screen.MovieDetails.name + "?movieId=${movie.id}")
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = Screen.MovieDetails.name + "?movieId={movieId}",
            arguments = listOf(
                navArgument(name = "movieId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            MovieDetailsScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
