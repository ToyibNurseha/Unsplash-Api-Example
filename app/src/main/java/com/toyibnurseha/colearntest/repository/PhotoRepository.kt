package com.toyibnurseha.colearntest.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.toyibnurseha.colearntest.api.UnsplashApi
import com.toyibnurseha.colearntest.data.FavoritePagingSource
import com.toyibnurseha.colearntest.data.PhotoPagingSource
import com.toyibnurseha.colearntest.data.local.PhotoFavoriteModel
import com.toyibnurseha.colearntest.database.UnsplashDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(var api: UnsplashApi, var database: UnsplashDatabase) {

    fun getSearchResults(query: String, color: String?, orientation: String?) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotoPagingSource(api, query, color, orientation) }
        ).liveData

    suspend fun upsert(photoData : PhotoFavoriteModel) = database.unsplashDao().upsert(photoData)

    suspend fun deleteFavorite(photoData: PhotoFavoriteModel) = database.unsplashDao().delete(photoData)

    fun getFavoriteById(id: String) = database.unsplashDao().getFavoriteById(id)

}