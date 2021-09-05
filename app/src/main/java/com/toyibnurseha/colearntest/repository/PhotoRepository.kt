package com.toyibnurseha.colearntest.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.toyibnurseha.colearntest.api.UnsplashApi
import com.toyibnurseha.colearntest.data.PhotoPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(var api: UnsplashApi) {

    fun getSearchResults(query: String, color: String?, orientation: String?) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotoPagingSource(api, query, color, orientation) }
        ).liveData
}