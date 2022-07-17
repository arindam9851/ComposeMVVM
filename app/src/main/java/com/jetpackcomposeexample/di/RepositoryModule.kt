package com.jetpackcomposeexample.di

import com.jetpackcomposeexample.network.RecipeService
import com.jetpackcomposeexample.network.model.RecipeDtoMapper
import com.jetpackcomposeexample.repository.RecipeRepository
import com.jetpackcomposeexample.repository.RecipeRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeMapper: RecipeDtoMapper,
    ): RecipeRepository {
        return RecipeRepositoryImplementation(
            recipeService = recipeService,
            mapper = recipeMapper
        )
    }
}