package com.mknishad.imovies.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mknishad.imovies.R
import com.mknishad.imovies.presentation.moviedetails.MovieDetailsScreen
import com.mknishad.imovies.presentation.movielist.MovieListScreen
import com.mknishad.imovies.presentation.splash.SplashScreen
import com.mknishad.imovies.presentation.wishlist.WishListScreen


@Composable
fun IMovieApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentRoute = backStackEntry?.destination?.route ?: Screen.MovieList.name
    // Extract the base route name before the '?'
    val baseRoute = currentRoute.substringBefore('?')
    val currentScreen = Screen.valueOf(
        baseRoute
    )

    val viewMode = hiltViewModel<MainViewModel>()

    Scaffold(
        topBar = {
            IMovieAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                navigateToWishlist = {
                    navController.navigate(Screen.Wishlist.name)
                }
            )
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = Screen.Splash.name) {
                SplashScreen(
                    onNavigateForward = {
                        navController.navigate(Screen.MovieList.name) {
                            popUpTo(Screen.Splash.name) {
                                inclusive = true
                            }
                        }
                    })
            }
            composable(route = Screen.MovieList.name) {
                MovieListScreen(
                    onMovieClick = { movie ->
                        navController.navigate(Screen.MovieDetails.name + "?movieId=${movie.id}")
                    }
                )
            }
            composable(route = Screen.Wishlist.name) {
                WishListScreen(
                    onMovieClick = { movie ->
                        navController.navigate(Screen.MovieDetails.name + "?movieId=${movie.id}")
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
                MovieDetailsScreen()
            }
        }
    }
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IMovieAppBar(
    currentScreen: Screen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navigateToWishlist: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            if (currentScreen != Screen.Splash) {
                Text(stringResource(currentScreen.title))
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = if (currentScreen != Screen.Splash) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (currentScreen == Screen.MovieList) {
                IconButton(onClick = { navigateToWishlist() }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = stringResource(R.string.back_button),
                        tint = Color.Red
                    )
                }
            }
        }
    )
}
