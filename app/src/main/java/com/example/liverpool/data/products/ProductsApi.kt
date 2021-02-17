package com.example.liverpool.data.products

import com.example.liverpool.domain.products.entity.ProductList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ProductsApi {
    fun getProducts(
        forcePlp: Boolean,
        keyword: String,
        page: Int,
        perPage: Int
    ):Single<ProductList>

    fun getAllSuggestions(): Single<List<String>>

    fun removeWordSuggestion(
        word: String
    ): Completable

    fun addWordSuggestion(
        word: String
    ): Completable

}