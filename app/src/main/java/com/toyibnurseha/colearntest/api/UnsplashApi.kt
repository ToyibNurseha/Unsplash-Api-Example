package com.toyibnurseha.colearntest.api

import com.toyibnurseha.colearntest.data.remote.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {

    @GET("search/photos")
    suspend fun getSearch(
        @Query("client_id") apiKey: String,
        @Query("client_secret") secretKey: String,
        @Query("query") query: String,
        @Query("per_page") perPage:Int,
        @Query("page") page:Int,
        @Query("color") color: String?,
        @Query("orientation") orientation: String?,
    ) : PhotoResponse

}