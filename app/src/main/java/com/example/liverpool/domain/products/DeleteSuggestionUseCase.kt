package com.example.liverpool.domain.products

import com.example.liverpool.data.products.ProductsApi

class DeleteSuggestionUseCase(private val productsApi: ProductsApi){
    operator fun invoke(keyword: String)  =
        productsApi.removeWordSuggestion(keyword)
            .andThen(productsApi.getAllSuggestions())
}