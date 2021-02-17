package com.example.liverpool.presentation.products

data class ProductViewData(
    val name: String="",
    val image: String="",
    val price: String="",
    val isLoading: Boolean = false
)