package com.example.liverpool.utils.customview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.LayoutRes
import com.example.liverpool.databinding.ItemAutocompleteBinding
import java.util.*

class CustomListAdapter (private val c: Context, @LayoutRes private val layoutResource: Int, private var suggestions: List<String>) :
    ArrayAdapter<String>(c, layoutResource, suggestions) {

    var filteredSuggestions: List<String> = listOf()

    override fun getCount(): Int = filteredSuggestions.size

    override fun getItem(position: Int): String = filteredSuggestions[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemAutocompleteBinding.bind(convertView ?: LayoutInflater.from(c).inflate(layoutResource, parent, false))
       binding.tvAutocomplete.text = filteredSuggestions[position]
        binding.btRemoveItem.setOnClickListener {
            RxBusCustomList.setEvent(getItem(position))
        }
        return binding.root
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredSuggestions = filterResults.values as List<String>

                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase(Locale.getDefault())

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    suggestions
                else
                    suggestions.filter {
                        it.toLowerCase(Locale.getDefault()).contains(queryString)
                    }
                return filterResults
            }
        }
    }

    fun updateSuggestionsList(suggestions: List<String>){
        this.suggestions = suggestions
        this.filteredSuggestions = suggestions
        notifyDataSetChanged()
    }
}