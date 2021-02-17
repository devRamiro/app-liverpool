package com.example.liverpool.presentation.products

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.api.load
import com.example.liverpool.databinding.HolderProductBinding

class ProductHolder(
    private val binding: HolderProductBinding
): ViewHolder(binding.root){

    fun bind(product: ProductViewData, listener: (ProductViewData) -> Unit){
        if(product.isLoading){
            binding.animLoading.visibility = View.VISIBLE
            binding.productContainer.visibility = View.GONE
        }else{
            binding.animLoading.visibility = View.GONE
            binding.productContainer.visibility = View.VISIBLE
            binding.tvName.text = product.name
            binding.tvPrice.text = product.price
            binding.ivPreview.load(product.image)
        }
        binding.cvProduct.setOnClickListener { listener(product) }
    }
}