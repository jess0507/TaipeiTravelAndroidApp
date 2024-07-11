package com.src.taipei_travel.ui.homeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.src.taipei_travel.R
import com.src.taipei_travel.data.remote.model.Attraction
import com.src.taipei_travel.databinding.FragmentHomeDetailBinding
import com.src.taipei_travel.ui.home.HomeItem
import com.src.taipei_travel.ui.share.ShareViewModel
import com.src.taipei_travel.ui.webview.WebViewState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeDetailFragment : Fragment() {
    private var binding: FragmentHomeDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeDetailBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeItemString = arguments?.getString("argJsonString")
        val homeItem = Gson().fromJson(homeItemString, HomeItem.AttractionItem::class.java)

        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = homeItem.attraction.name

        binding?.let {
            it.time.text = homeItem.attraction.open_time
            it.address.text = homeItem.attraction.address
            Glide.with(it.root.context)
                .load(homeItem.attraction.images.firstOrNull()?.src)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(RequestOptions().override(400, 200))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(it.image)
            it.webUrl.text = homeItem.attraction.url
            it.webUrl.setOnClickListener {
                val webViewState = WebViewState(homeItem.attraction.name, homeItem.attraction.url)
                val bundle = bundleOf("argJsonString" to Gson().toJson(webViewState))
                findNavController()
                    .navigate(R.id.action_fragment_home_detail_to_fragment_webview, bundle)
            }
            it.content.text = homeItem.attraction.introduction
        }
    }
}