package com.toyibnurseha.colearntest.ui.viewModel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.toyibnurseha.colearntest.data.local.MyPhoto
import com.toyibnurseha.colearntest.repository.PhotoRepository

class MainViewModel @ViewModelInject constructor(private val repo: PhotoRepository, @Assisted state: SavedStateHandle) : ViewModel() {

    companion object {
        private const val CURRENT_QUERY = "unique_query!"
        private const val DEFAULT_QUERY = "Football"
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    fun getPhotos(query: String, color: String?, orientation: String?) : LiveData<PagingData<MyPhoto>> {
        currentQuery.value = query
        return currentQuery.switchMap { queryString ->
            repo.getSearchResults(queryString, color, orientation).cachedIn(viewModelScope)
        }
    }

}