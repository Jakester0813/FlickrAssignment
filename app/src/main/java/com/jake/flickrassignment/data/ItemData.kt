package com.jake.flickrassignment.data

import com.fasterxml.jackson.annotation.JsonProperty

data class FlickrResponse(
    val title: String,
    val link: String,
    val description: String,
    val modified: String,
    val generator: String,
    val items: List<ItemData>
)

data class ItemData(
    val title: String?,
    val link: String,
    val media: Media,
    @JsonProperty("date_taken")
    val dateTaken: String?,
    val description: String?,
    val publishedDate: String?,
    val author: String?,
    @JsonProperty("date_taken")
    val authorId: String?,
    val tags: String
)

data class Media(val m: String)