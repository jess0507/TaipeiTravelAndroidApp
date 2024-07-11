package com.src.taipei_travel.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.src.taipei_travel.R
import com.src.taipei_travel.databinding.ItemHomeCategoryBinding
import com.src.taipei_travel.ui.webview.WebViewState

class HomeCategoryAdapter(
    private var categoryList: List<HomeCategory> = listOf(),
    private var translations: Map<String, String> = mapOf(),
) : RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(private val itemBinding: ItemHomeCategoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(category: HomeCategory, translations: Map<String, String>) {
            val firstItem: HomeItem = category.list.firstOrNull() ?: return
            itemBinding.title.text = translations[firstItem.name]
            val homeListAdapter = when(firstItem) {
                is HomeItem.AttractionItem -> {
                    HomeListAdapter {
                        val bundle = bundleOf("argJsonString" to Gson().toJson(it))
                        findNavController(itemBinding.root).navigate(R.id.action_fragment_home_to_fragment_home_detail, bundle)
                    }
                }
                is HomeItem.NewItem -> {
                    HomeListAdapter {
                        val new = if (it is HomeItem.NewItem) it.new else return@HomeListAdapter
                        val webViewState = WebViewState(new.title, new.url)
                        val bundle = bundleOf("argJsonString" to Gson().toJson(webViewState))
                        findNavController(itemBinding.root).navigate(R.id.action_fragment_home_to_fragment_webview, bundle)
                    }
                }
            }
            itemBinding.categoryList.layoutManager = LinearLayoutManager(itemBinding.root.context)
            itemBinding.categoryList.adapter = homeListAdapter
            homeListAdapter.updateItems(category.list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemHomeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position], translations)
    }

    fun updateItems(categories: List<HomeCategory>) {
        categoryList = categories
        notifyDataSetChanged()
    }

    fun updateTranslations(trans: Map<String, String>) {
        translations = trans
        notifyDataSetChanged()
    }
}
