package com.toyibnurseha.colearntest.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toyibnurseha.colearntest.utils.Constant.FAVORITE_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = FAVORITE_TABLE_NAME)
data class PhotoFavoriteModel(
    @PrimaryKey
    var id: String,
    var desc: String? = "",
    var urlBig: String? = "",
    var urlThumb: String? = "",
) : Parcelable