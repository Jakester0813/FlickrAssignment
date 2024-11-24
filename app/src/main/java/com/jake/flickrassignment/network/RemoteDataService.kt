package com.jake.flickrassignment.network

import com.jake.flickrassignment.data.FlickrResponse
import com.jake.flickrassignment.data.ImageData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RemoteDataService @Inject constructor(private val imageService: ImageService){
    suspend fun getImages(input: String) : List<ImageData> {
        return fromResponse(imageService.getImages(tags = input))
    }

    private fun fromResponse(response: FlickrResponse) : List<ImageData> {
        val imagesList = mutableListOf<ImageData>()
        val itemsList = response.items
        for (item in itemsList) {
            val imageUrl = item.media.m
            val imageTitle = item.title ?: "No title"
            val author = item.author?.substringAfter("\"")?.substringBefore("\"") ?: "Some Author"
            val title = " $imageTitle by $author"
            val description = item.description ?: "No description provided"
            val dateFormat = if (!item.publishedDate.isNullOrBlank()) LocalDate.parse(item.publishedDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME) else null
            val publishedDate = if (dateFormat != null) "${dateFormat.month}/${dateFormat.dayOfMonth}/${dateFormat.year}" else "No date provided"
            imagesList.add(ImageData(imageUrl, title, description, publishedDate))
        }
        return imagesList.toList()
    }
}