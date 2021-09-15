package com.toyibnurseha.colearntest.data

import androidx.paging.PagingSource
import com.toyibnurseha.colearntest.data.local.PhotoFavoriteModel
import com.toyibnurseha.colearntest.database.UnsplashDAO

class FavoritePagingSource(private val unsplashDao: UnsplashDAO) : PagingSource<Int, PhotoFavoriteModel>() {
    private companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoFavoriteModel> {
        val position = params.key ?: STARTING_PAGE_INDEX

        val photos = unsplashDao.getAllFavoritePhotos(params.loadSize)

        return LoadResult.Page(
            data = photos,
            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
            nextKey = if (position == STARTING_PAGE_INDEX) null else position + 1
        )
    }
}