package com.toyibnurseha.colearntest.ui.viewModel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.toyibnurseha.colearntest.data.FavoritePagingSource
import com.toyibnurseha.colearntest.data.local.MyPhoto
import com.toyibnurseha.colearntest.data.local.PhotoFavoriteModel
import com.toyibnurseha.colearntest.repository.PhotoRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val repo: PhotoRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val CURRENT_QUERY = "unique_query!"
        private const val DEFAULT_QUERY = "Football"
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    fun getPhotos(
        query: String,
        color: String?,
        orientation: String?
    ): LiveData<PagingData<MyPhoto>> {
        currentQuery.value = query
        return currentQuery.switchMap { queryString ->
            repo.getSearchResults(queryString, color, orientation).cachedIn(viewModelScope)
        }
    }


    fun getFavorites() =
        Pager(PagingConfig(10)) { FavoritePagingSource(repo.database.unsplashDao()) }.flow.cachedIn(
            viewModelScope
        )

    fun upsert(photoData: MyPhoto) = viewModelScope.launch {
        val newPhotoEntity = PhotoFavoriteModel(
            id = photoData.id,
            desc = photoData.desc,
            urlThumb = photoData.urls?.small,
            urlBig = photoData.urls?.full
        )
        repo.upsert(newPhotoEntity)
    }

    fun getFavoriteById(id: String) = repo.getFavoriteById(id)

    fun deletePhoto(data: MyPhoto) = viewModelScope.launch {
        val newPhotoEntity = PhotoFavoriteModel(
            data.id,
            data.desc,
            data.urls?.full,
            data.urls?.small
        )
        repo.deleteFavorite(newPhotoEntity)
    }

}