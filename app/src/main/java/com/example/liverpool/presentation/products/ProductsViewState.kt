package com.example.liverpool.presentation.products

sealed class ProductsViewState {
    object Loading : ProductsViewState()
    data class DrawSuggestion(val suggestions: List<String>): ProductsViewState()
    data class Ready(val products: List<ProductViewData>, val IsLastPage: Boolean) : ProductsViewState()
    data class Error(val errorMessage: String) : ProductsViewState()
}