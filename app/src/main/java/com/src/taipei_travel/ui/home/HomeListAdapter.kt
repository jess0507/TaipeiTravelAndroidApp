package com.src.taipei_travel.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.src.taipei_travel.R
import com.src.taipei_travel.data.remote.model.Attraction
import com.src.taipei_travel.data.remote.model.New
import com.src.taipei_travel.databinding.ItemAttractionBinding
import com.src.taipei_travel.databinding.ItemNewBinding
import timber.log.Timber

class HomeListAdapter(
    private var items: List<HomeItem> = listOf(),
    private val onItemClick: (HomeItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ATTRACTION = 0
        private const val TYPE_NEW = 1
    }

    // ViewHolder for Attraction
    class AttractionViewHolder(private val itemBinding: ItemAttractionBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(attraction: Attraction, onItemClick: (HomeItem) -> Unit) {
            itemBinding.title.text = attraction.name
            itemBinding.content.text = attraction.introduction
            Glide.with(itemBinding.root.context)
                .load(attraction.images.firstOrNull()?.src)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(RequestOptions().override(200, 200))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(itemBinding.image)
            itemBinding.card.setOnClickListener {
                onItemClick(HomeItem.AttractionItem(attraction))
            }
        }
    }

    // ViewHolder for New
    class NewViewHolder(private val itemBinding: ItemNewBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(news: New, onItemClick: (HomeItem) -> Unit) {
            itemBinding.title.text = news.title
            itemBinding.content.text = news.description
            itemBinding.card.setOnClickListener {
                onItemClick(HomeItem.NewItem(news))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeItem.AttractionItem -> TYPE_ATTRACTION
            is HomeItem.NewItem -> TYPE_NEW
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ATTRACTION -> {
                val binding = ItemAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AttractionViewHolder(binding)
            }
            TYPE_NEW -> {
                val binding = ItemNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NewViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is HomeItem.AttractionItem -> (holder as AttractionViewHolder).bind(item.attraction, onItemClick)
            is HomeItem.NewItem -> (holder as NewViewHolder).bind(item.new, onItemClick)
        }
    }

    fun updateItems(homeItemList: List<HomeItem>) {
        items = homeItemList
        notifyDataSetChanged()
    }
}
