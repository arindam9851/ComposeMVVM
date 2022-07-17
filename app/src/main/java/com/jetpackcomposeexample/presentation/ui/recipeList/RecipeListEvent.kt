package com.jetpackcomposeexample.presentation.ui.recipeList

sealed class RecipeListEvent{
    object NewSearchEvent : RecipeListEvent()

    object NextPageEvent : RecipeListEvent()

    // restore after process death
    object RestoreStateEvent: RecipeListEvent()
}
