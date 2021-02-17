package com.example.liverpool.data.products.entities

import com.google.gson.annotations.SerializedName

data class Record(
    @SerializedName("productId")
    val id: Int,
    @SerializedName("productDisplayName")
    val title: String,
    @SerializedName("listPrice")
    val price: Double,
    @SerializedName("smImage")
    val image: String
)