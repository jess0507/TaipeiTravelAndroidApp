package com.src.taipei_travel.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.src.taipei_travel.R
import com.src.taipei_travel.databinding.FragmentSettingBinding
import com.src.taipei_travel.ui.settingDetail.model.SettingOption
import com.src.taipei_travel.ui.share.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private lateinit var settingsAdapter: SettingsAdapter
    private val shareViewModel: ShareViewModel by activityViewModels()
    private var binding: FragmentSettingBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recycleView?.layoutManager = LinearLayoutManager(context)
        settingsAdapter = SettingsAdapter(SettingOption.getAllOptions(), showCheck = false) {
            if (it is SettingOption) {
                val bundle = bundleOf("argJsonString" to Gson().toJson(it))
                findNavController().navigate(
                    R.id.action_fragment_setting_to_fragment_setting_detail,
                    bundle
                )
            }
        }
        binding?.recycleView?.adapter = settingsAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                shareViewModel.translations.collect {
                    val toolbar = (activity as AppCompatActivity).supportActionBar
                    toolbar?.title = it["setting_title"]

                    settingsAdapter.updateTranslations(it)
                }
            }
        }
    }
}