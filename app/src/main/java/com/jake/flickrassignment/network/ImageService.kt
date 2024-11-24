package com.jake.flickrassignment.network

import com.jake.flickrassignment.data.FlickrResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET("services/feeds/photos_public.gne")
    suspend fun getImages(
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJSONCallback: String = "1",
        @Query("tags") tags: String
    ) : FlickrResponse
}