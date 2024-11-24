package com.jake.flickrassignment.network

import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val remoteDataService: RemoteDataService
) : BaseAPIResponse() {
    suspend fun getImages(input: String) = safeApiCall {
        remoteDataService.getImages(input)
    }
}