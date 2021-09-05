package com.toyibnurseha.colearntest.data

import androidx.paging.PagingSource
import com.toyibnurseha.colearntest.BuildConfig
import com.toyibnurseha.colearntest.api.UnsplashApi
import com.toyibnurseha.colearntest.data.local.MyPhoto
import com.toyibnurseha.colearntest.utils.Constant
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class PhotoPagingSource(
    private var api: UnsplashApi,
    private var query: String,
    private var color: String?,
    private var orientation: String?
) : PagingSource<Int, MyPhoto>() {

    private val apiKey = BuildConfig.CLIENT_ID
    private val secretKey = BuildConfig.SECRET_KEY

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyPhoto> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.getSearch(
                apiKey = apiKey,
                secretKey = secretKey,
                query = query,
                page = page,
                perPage = Constant.TOTAL_QUERY_PAGE,
                color = color,
                orientation = orientation
            )
            val photos: List<MyPhoto> = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (photos.isEmpty()) null else page + 1
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }


    }
}