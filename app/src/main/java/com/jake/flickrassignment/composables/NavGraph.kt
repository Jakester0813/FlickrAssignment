package com.jake.flickrassignment.composables

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jake.flickrassignment.main.MainViewModel

@Composable
fun NavGraph (
    viewModel: MainViewModel,
    navController: NavHostController
) {

    NavHost(navController = navController,
        startDestination = "search_screen") {
        composable("search_screen") {
            SearchScreen(viewModel)
        }
        composable("image_detail_screen") {
            ImageDetailScreen(viewModel, navController)
        }
    }
}