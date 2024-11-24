package com.jake.flickrassignment.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.jake.flickrassignment.composables.NavGraph
import com.jake.flickrassignment.main.MainReducer.MainEffect
import com.jake.flickrassignment.ui.theme.FlickrAssignmentTheme
import com.jake.flickrassignment.utils.rememberFlowWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrAssignmentTheme {
                val navController = rememberNavController()
                val effect = rememberFlowWithLifecycle(mainViewModel.effect)
                LaunchedEffect(effect) {
                    effect.collect { action ->
                        when (action) {
                            is MainEffect.NavigateToImageDetail -> {
                                navController.navigate("image_detail_screen")
                            }
                        }
                    }
                }
                NavGraph(mainViewModel, navController)
            }
        }
    }
}
