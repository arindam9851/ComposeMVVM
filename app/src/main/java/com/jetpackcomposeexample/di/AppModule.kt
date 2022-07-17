package com.jetpackcomposeexample.di

import android.content.Context
import com.jetpackcomposeexample.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


/*
    This class provide the app context as a dependency throughout the application
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app : Context) : BaseApplication{
        return  app as BaseApplication
    }
}