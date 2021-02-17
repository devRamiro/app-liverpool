package com.example.liverpool.data.products.database

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface WordsSuggestionDao {

    @Query("SELECT keyword FROM WordEntity")
    fun getAll(): Single<List<WordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(keyword: WordEntity): Completable

    @Query("DELETE FROM WordEntity WHERE keyword = :keyword")
    fun delete(keyword: String): Completable
}