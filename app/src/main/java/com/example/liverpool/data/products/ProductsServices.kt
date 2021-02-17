package com.example.liverpool.data.products

import com.example.liverpool.data.products.entities.ProductsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsServices {

    @GET("plp")
    fun getProducts(
        @Query("force-plp") forcePlp: Boolean,
        @Query("search-string") keyword: String,
        @Query("page-number") page: Int,
        @Query("number-of-items-per-page") perPage: Int
    ): Single<ProductsResponse>

}