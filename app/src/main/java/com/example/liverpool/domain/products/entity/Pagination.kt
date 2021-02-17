package com.example.liverpool.domain.products.entity

data class Pagination(
    val page: Int,
    val perPage: Int,
    val isLastPage: Boolean
)