package com.jetpackcomposeexample.network.response

import com.google.gson.annotations.SerializedName
import com.jetpackcomposeexample.network.model.RecipeDto

data class RecipeSearchResponse(
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeDto>,
)