package com.toyibnurseha.colearntest.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.toyibnurseha.colearntest.data.local.MyPhoto
import com.toyibnurseha.colearntest.data.local.PhotoFavoriteModel

@Dao
interface UnsplashDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(photoData: PhotoFavoriteModel)

    @Delete
    suspend fun delete(photoData: PhotoFavoriteModel)

    @Query("SELECT * FROM FAVORITE_TB LIMIT :size")
    suspend fun getAllFavoritePhotos(size: Int) : List<PhotoFavoriteModel>

    @Query("SELECT * FROM FAVORITE_TB WHERE id = :id")
    fun getFavoriteById(id: String) : LiveData<PhotoFavoriteModel>

}