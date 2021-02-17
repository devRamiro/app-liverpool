package com.example.liverpool.data.products.entities

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    val status: Status,
    @SerializedName("plpResults")
    val results: PlpResults
)