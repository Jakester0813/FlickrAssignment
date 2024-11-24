package com.jake.flickrassignment.main

import androidx.compose.runtime.Immutable
import com.jake.flickrassignment.base.Reducer
import com.jake.flickrassignment.data.ImageData

class MainReducer :
    Reducer<MainReducer.MainState, MainReducer.MainEvent, MainReducer.MainEffect> {

    @Immutable
    data class MainState(
        val initialLoad: Boolean,
        val imagesLoading: Boolean,
        val images: List<ImageData>,
        val errorMessage: String
    ) : Reducer.ViewState {
        companion object {
            fun initial(): MainState {
                return MainState(
                    initialLoad = true,
                    imagesLoading = false,
                    images = emptyList(),
                    errorMessage = ""
                )
            }
        }
    }

    @Immutable
    sealed class MainEvent : Reducer.ViewEvent {
        data class ImagesLoading(val initialLoad: Boolean, val isImagesLoading: Boolean) : MainEvent()
        data class ImagesSuccess(val isImagesLoading: Boolean, val images: List<ImageData>) : MainEvent()
        data class ImagesFailure(val isImagesLoading: Boolean, val errorMsg: String) : MainEvent()
    }

    @Immutable
    sealed class MainEffect : Reducer.ViewEffect {
        data class NavigateToImageDetail(val index: Int) : MainEffect()
    }

    override fun reduce(previousState: MainState, event: MainEvent): Pair<MainState, MainEffect?> {
        return when (event) {
            is MainEvent.ImagesLoading -> {
                previousState.copy(
                    imagesLoading = event.isImagesLoading
                ) to null
            }
            is MainEvent.ImagesSuccess -> {
                previousState.copy(
                    imagesLoading = event.isImagesLoading,
                    images = event.images
                ) to null
            }
            is MainEvent.ImagesFailure -> {
                previousState.copy(
                    imagesLoading = event.isImagesLoading,
                    errorMessage = event.errorMsg
                ) to null
            }
        }
    }
}
