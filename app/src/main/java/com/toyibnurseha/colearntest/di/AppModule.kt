package com.toyibnurseha.colearntest.di

import android.content.Context
import androidx.room.Room
import com.toyibnurseha.colearntest.api.UnsplashApi
import com.toyibnurseha.colearntest.database.UnsplashDatabase
import com.toyibnurseha.colearntest.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext app: Context
    ) =  Room.databaseBuilder(
        app.applicationContext,
        UnsplashDatabase::class.java,
        Constant.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideDao(db: UnsplashDatabase) = db.unsplashDao()

}