package com.example.liverpool.data.products.entities

import com.google.gson.annotations.SerializedName

data class PlpResults (
    @SerializedName("plpState")
    val pagination: PlpState,
    @SerializedName("records")
    val products: List<Record>
)