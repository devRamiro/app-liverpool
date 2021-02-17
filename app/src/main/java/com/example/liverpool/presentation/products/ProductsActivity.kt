package com.example.liverpool.presentation.products

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.liverpool.R
import com.example.liverpool.databinding.ActivityProductsBinding
import com.example.liverpool.utils.customview.CustomListAdapter
import com.example.liverpool.utils.customview.PaginationScrollListener
import com.example.liverpool.utils.customview.RxBusCustomList
import com.example.liverpool.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsActivity : AppCompatActivity() {
    private lateinit var viewModel: ProductsViewModel
    private lateinit var binding: ActivityProductsBinding
    private lateinit var adapterSuggestions: CustomListAdapter
    private lateinit var productAdapter: ProductAdapter

    //pagination
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var page: Int = 1
    private var keyword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)

        binding.ivSearch.setOnClickListener {
            productAdapter.clear()
            searchKeyword(newQuery = true)
        }

        binding.etSearch.onItemClickListener = onItemClickListener
        adapterSuggestions = CustomListAdapter(this, R.layout.item_autocomplete, emptyList())
        binding.etSearch.setAdapter(adapterSuggestions)

        binding.etSearch.setOnEditorActionListener(
            OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    productAdapter.clear()
                    searchKeyword(newQuery = true)
                    return@OnEditorActionListener true
                }
                false
            })

        binding.rvProducts.addOnScrollListener(object : PaginationScrollListener(binding.rvProducts.layoutManager as GridLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                searchKeyword(newQuery = false)
            }
        })

        productAdapter = ProductAdapter{
            Snackbar.make(binding.root,it.name,Snackbar.LENGTH_SHORT).show()
        }
        binding.rvProducts.adapter = productAdapter

        RxBusCustomList.getEvent()?.subscribe {
            viewModel.deleteWordFromList(it)
        }

        initViewState()
        viewModel.getLastSuggestions()
        setContentView(binding.root)
    }

    private fun initViewState(){
        viewModel.viewState.observe(this, Observer {
            when (it) {
                ProductsViewState.Loading -> showLoading(true)
                is ProductsViewState.Ready -> drawProducts(it.products, it.IsLastPage)
                is ProductsViewState.DrawSuggestion -> adapterSuggestions.updateSuggestionsList(it.suggestions)
                is ProductsViewState.Error -> {
                    Snackbar.make(binding.root,it.errorMessage,Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    private val onItemClickListener = OnItemClickListener {
            adapterView, _, i, _ ->
        productAdapter.clear()
        searchKeyword(adapterView.getItemAtPosition(i) as String, true)
    }

    private fun searchKeyword(
        keyword: String = binding.etSearch.text.toString(),
        newQuery: Boolean
    ){
        this.keyword = keyword
        binding.root.hideKeyboard()

        if(newQuery) page=1
        else page+=1

        viewModel.searchProduct(keyword,page)
        binding.etSearch.dismissDropDown()
    }

    private fun showLoading(loading: Boolean) {
        isLoading = loading
        if(loading){
            productAdapter.showLoading()
            binding.pgLoad.visibility = View.VISIBLE
            binding.ivSearch.visibility = View.INVISIBLE
        }else{
            productAdapter.hideLoading()
            binding.pgLoad.visibility = View.GONE
            binding.ivSearch.visibility = View.VISIBLE
        }
    }

    private fun drawProducts(products: List<ProductViewData>, isLastPage: Boolean){
        this.isLastPage = isLastPage
        showLoading(false)
        binding.containerEmpty.root.visibility = View.GONE
        productAdapter.addProducts(products)
    }
}
