package com.toyibnurseha.colearntest.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyPhoto(
    var id: String = "",
    var desc: String? = "",
    var urls: PhotoUrls? = null,
    val user: User? = null
) : Parcelable {

    @Parcelize
    data class PhotoUrls(
        var raw: String = "",
        var full: String = "",
        var regular: String = "",
        var thumb: String = "",
        var small: String = ""
    ) : Parcelable

    @Parcelize
    data class User(
        var name: String,
    ) : Parcelable
}