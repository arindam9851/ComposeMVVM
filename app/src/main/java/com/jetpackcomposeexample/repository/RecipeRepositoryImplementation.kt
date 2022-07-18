package com.jetpackcomposeexample.repository

import com.jetpackcomposeexample.domain.model.Recipe
import com.jetpackcomposeexample.network.RecipeService
import com.jetpackcomposeexample.network.model.RecipeDtoMapper

class RecipeRepositoryImplementation(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper,
) : RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(
            recipeService.search(
                token = token,
                page = page,
                query = query
            ).recipes
        )
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(recipeService.get(token = token, id))
    }


}