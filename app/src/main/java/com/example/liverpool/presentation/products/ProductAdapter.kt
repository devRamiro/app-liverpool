package com.example.liverpool.presentation.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.liverpool.databinding.HolderProductBinding

class ProductAdapter(
    private val listener: (ProductViewData) -> Unit
) : RecyclerView.Adapter<ProductHolder>(){

    private var products: MutableList<ProductViewData> = arrayListOf()

    fun clear(){
        this.products.clear()
        notifyDataSetChanged()
    }
    
    fun addProducts(products: List<ProductViewData>){
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    fun showLoading(){
        this.products.add(ProductViewData(isLoading = true))
        notifyDataSetChanged()
    }

    fun hideLoading(){
        this.products.map {
            if(it.isLoading){
                this.products.remove(it)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductHolder = ProductHolder(
        HolderProductBinding.inflate(LayoutInflater.from(parent.getContext()))
    )

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductHolder,
        position: Int
    ) = holder.bind(
        products[position],
        listener
    )

}