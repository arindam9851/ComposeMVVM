package com.jetpackcomposeexample.presentation.ui.recipeList

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpackcomposeexample.domain.model.Recipe
import com.jetpackcomposeexample.repository.RecipeRepository
import com.jetpackcomposeexample.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

class RecipeListViewModel
@ViewModelInject
constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Float = 0f

    val loading = mutableStateOf(false)

    // Pagination starts at '1' (-1 = exhausted)
    val page = mutableStateOf(1)

    var recipeListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            setListScrollPosition(p)
        }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }

        if (recipeListScrollPosition != 0) {
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }

    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeListEvent.NewSearchEvent -> {
                        newSearch()
                    }
                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()
                    }
                    is RecipeListEvent.RestoreStateEvent -> {
                        restoreState()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private suspend fun restoreState() {
        loading.value = true
        val results: MutableList<Recipe> = mutableListOf()
        for (p in 1..page.value) {
            val result = repository.search(
                token = token,
                page = p,
                query = query.value
            )
            results.addAll(result)
            if (p == page.value) { // done
                recipes.value = results
                loading.value = false
            }
        }
    }

    private suspend fun newSearch() {
        loading.value = true

        resetSearchState()

        delay(2000)

        val result = repository.search(
            token = token,
            page = 1,
            query = query.value
        )
        recipes.value = result

        loading.value = false
    }

    private suspend fun nextPage() {
        // prevent duplicate event due to recompose happening to quickly
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            // just to show pagination, api is fast
            delay(1000)

            if (page.value > 1) {
                val result =
                    repository.search(token = token, page = page.value, query = query.value)
                Log.d(TAG, "search: appending")
                appendRecipes(result)
            }
            loading.value = false
        }
    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }


}