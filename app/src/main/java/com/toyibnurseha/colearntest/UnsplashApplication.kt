package com.toyibnurseha.colearntest

import android.app.Application
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UnsplashApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))
    }
}