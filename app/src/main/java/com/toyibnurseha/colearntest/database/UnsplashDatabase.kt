package com.toyibnurseha.colearntest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.toyibnurseha.colearntest.data.local.PhotoFavoriteModel

@Database(entities = [PhotoFavoriteModel::class], version = 1)
abstract class UnsplashDatabase : RoomDatabase() {

    abstract fun unsplashDao(): UnsplashDAO

}