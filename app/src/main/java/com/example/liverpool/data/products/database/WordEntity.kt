package com.example.liverpool.data.products.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordEntity(
    @PrimaryKey
    val keyword: String
)