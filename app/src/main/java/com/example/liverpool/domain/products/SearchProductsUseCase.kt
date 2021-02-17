package com.example.liverpool.domain.products

import com.example.liverpool.data.products.ProductsApi
import com.example.liverpool.domain.products.entity.ProductList
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class SearchProductsUseCase(private val productsApi: ProductsApi){
    private val productsPerPage: Int = 10

    operator fun invoke(keyword: String, currentPage: Int): Single<ProductList> =
        productsApi.getProducts(
            true,
            keyword,
            currentPage,
            productsPerPage
        ).flatMap {
            productsApi.addWordSuggestion(keyword)
                .andThen(Single.just(it))
        }.delay(800,TimeUnit.MILLISECONDS)
}