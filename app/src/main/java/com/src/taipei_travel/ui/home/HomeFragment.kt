package com.src.taipei_travel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.taipei_travel.databinding.FragmentHomeBinding
import com.src.taipei_travel.ui.share.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private val viewmodel by viewModels<HomeViewModel>()
    private val shareViewModel by activityViewModels<ShareViewModel>()

    private lateinit var homeCategoryAdapter: HomeCategoryAdapter

    private var binding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleRecycleView()
        handleObservers()
    }

    private fun handleRecycleView() {
        homeCategoryAdapter = HomeCategoryAdapter()
        binding?.recycleView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recycleView?.adapter = homeCategoryAdapter

        // Add scroll listener
        binding?.recycleView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewmodel.loadMoreList()
                }
            }
        })
    }

    private fun handleObservers() {
        shareViewModel.settings.observe(viewLifecycleOwner) {
            Timber.d("settings: $it")
            viewmodel.initialList(it.language)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                shareViewModel.translations.collect {
                    val toolbar = (activity as AppCompatActivity).supportActionBar
                    toolbar?.title = it["home_title"]

                    homeCategoryAdapter.updateTranslations(it)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.homeCategoryState.collect {
                    homeCategoryAdapter.updateItems(it.list)
                }
            }
        }

        viewmodel.errorMsg.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Timber.d("errorMsg: $it")
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewmodel.showProgressBar.observe(viewLifecycleOwner) {
            if (it) binding?.progressBar?.visibility = View.VISIBLE
            else binding?.progressBar?.visibility = View.INVISIBLE
        }
    }
}