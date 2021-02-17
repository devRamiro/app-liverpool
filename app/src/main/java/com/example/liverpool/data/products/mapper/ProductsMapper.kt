package com.example.liverpool.data.products.mapper

import com.example.liverpool.data.products.entities.ProductsResponse
import com.example.liverpool.domain.products.entity.Pagination
import com.example.liverpool.domain.products.entity.Product
import com.example.liverpool.domain.products.entity.ProductList

internal fun ProductsResponse.toProductList(): ProductList =
    ProductList(
        Pagination(
            this.results.pagination.firstRecNum,
            this.results.pagination.firstRecNum,
            this.results.pagination.totalNumRecs <= this.results.pagination.lastRecNum),
        this.results.products.map {
            Product(
                it.id,
                it.title,
                it.price,
                it.image)
        }
    )

