package com.example.liverpool.domain.products

import com.example.liverpool.data.products.ProductsApi
import io.reactivex.rxjava3.core.Single

class GetSuggestionsUseCase(private val productsApi: ProductsApi){
    operator fun invoke(): Single<List<String>> =
        productsApi.getAllSuggestions()
}