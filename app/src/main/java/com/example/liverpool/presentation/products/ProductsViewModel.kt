package com.example.liverpool.presentation.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.liverpool.domain.products.DeleteSuggestionUseCase
import com.example.liverpool.domain.products.SearchProductsUseCase
import com.example.liverpool.domain.products.GetSuggestionsUseCase
import com.example.liverpool.domain.products.entity.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
    private val deleteSuggestionUseCase: DeleteSuggestionUseCase,
    private val getSuggestionsUseCase: GetSuggestionsUseCase
): ViewModel() {

    val viewState: MutableLiveData<ProductsViewState> = MutableLiveData()
    private val disposable = CompositeDisposable()

    fun searchProduct(keyword: String, page: Int) {
        viewState.value = ProductsViewState.Loading
        disposable
            .add(
                searchProductsUseCase.invoke(keyword,page)
                    .flatMap {
                        getLastSuggestions()
                        return@flatMap Single.just(it)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        viewState.value = ProductsViewState.Ready(
                            it.products.toProductsViewData(),
                            it.pagination.isLastPage
                        )
                    }, {
                        viewState.value = ProductsViewState.Error(it.message ?: "")
                    })
            )
    }

    fun getLastSuggestions() {
        getSuggestionsUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewState.value = ProductsViewState.DrawSuggestion(it)
                }, {}
            )
    }

    fun deleteWordFromList(keyword: String){
        deleteSuggestionUseCase.invoke(keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewState.value = ProductsViewState.DrawSuggestion(it)
                }, {})
    }

    private fun List<Product>.toProductsViewData(): List<ProductViewData> = this.map {
        ProductViewData(
            it.title,
            it.image,
            "$${it.price}",
            false
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}