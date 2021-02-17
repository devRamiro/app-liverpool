package com.example.liverpool.data.products.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WordEntity::class],
    version = 1
)
abstract class ProductsDb : RoomDatabase(){
    abstract fun suggestionsDao(): WordsSuggestionDao
}