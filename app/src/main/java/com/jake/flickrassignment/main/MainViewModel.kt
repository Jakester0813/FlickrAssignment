package com.jake.flickrassignment.main

import androidx.lifecycle.viewModelScope
import com.jake.flickrassignment.base.BaseViewModel
import com.jake.flickrassignment.data.ImageData
import com.jake.flickrassignment.main.MainReducer.MainEffect
import com.jake.flickrassignment.main.MainReducer.MainEvent
import com.jake.flickrassignment.main.MainReducer.MainState
import com.jake.flickrassignment.network.ImageRepository
import com.jake.flickrassignment.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ImageRepository
) : BaseViewModel<MainState, MainEvent, MainEffect>(
    initialState = MainState.initial(),
    reducer = MainReducer()
) {
    var images = emptyList<ImageData>()
    lateinit var selectedImage: ImageData

    fun getImages(tags: String) {
        viewModelScope.launch {
            sendEvent(event = MainEvent.ImagesLoading(initialLoad = false, isImagesLoading = true))
            when (val imageResponse = repository.getImages(tags)) {
                is NetworkResult.Success -> {
                    images = imageResponse.data!!.toList()
                    sendEvent(
                        event = MainEvent.ImagesSuccess(
                            isImagesLoading = false,
                            images = images
                        )
                    )
                }
                is NetworkResult.Error -> {
                    sendEvent(
                        event = MainEvent.ImagesFailure(
                            isImagesLoading = false,
                            errorMsg = imageResponse.error!!
                        )
                    )
                }
            }
        }
    }

    fun onImageClick(index: Int) {
        selectedImage = images[index]
        sendEffect(effect = MainEffect.NavigateToImageDetail(index))
    }
}