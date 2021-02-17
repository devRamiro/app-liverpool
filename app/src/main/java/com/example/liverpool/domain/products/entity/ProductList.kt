package com.example.liverpool.domain.products.entity

data class ProductList(
    val pagination: Pagination,
    val products: List<Product>
)