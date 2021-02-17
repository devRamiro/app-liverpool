package com.example.liverpool.data.products

import com.example.liverpool.data.products.database.WordEntity
import com.example.liverpool.data.products.database.WordsSuggestionDao
import com.example.liverpool.data.products.mapper.toProductList
import com.example.liverpool.domain.products.entity.ProductList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductsApiImpl(
    private val productsServices: ProductsServices,
    private val wordsSuggestionDao: WordsSuggestionDao
) :
    ProductsApi {

    override fun getProducts(
        forcePlp: Boolean,
        keyword: String,
        page: Int,
        perPage: Int
    ): Single<ProductList> = productsServices
        .getProducts(forcePlp,keyword,page,perPage)
        .map { it.toProductList() }

    override fun getAllSuggestions(): Single<List<String>> = wordsSuggestionDao
        .getAll()
        .map { listWordsEntity ->
            listWordsEntity.map {
                it.keyword
            }
        }

    override fun removeWordSuggestion(word: String): Completable = wordsSuggestionDao.delete(word)

    override fun addWordSuggestion(word: String):Completable =
        wordsSuggestionDao.insert(
        WordEntity(keyword = word))
}