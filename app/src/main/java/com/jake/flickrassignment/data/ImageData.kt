package com.jake.flickrassignment.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageData(
    val imgUrl: String,
    val title: String,
    val description: String,
    val publishedDate: String
) : Parcelable

fun ImageData.getImageNameForContentDescription() : String {
    val shortenedTitle = title.split("by")[0]
    return "Image of $shortenedTitle"
}