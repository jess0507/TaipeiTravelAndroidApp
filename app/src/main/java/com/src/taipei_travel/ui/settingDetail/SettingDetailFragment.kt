package com.src.taipei_travel.ui.settingDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.src.taipei_travel.databinding.FragmentSettingDetailBinding
import com.src.taipei_travel.ui.setting.SettingsAdapter
import com.src.taipei_travel.ui.settingDetail.model.Option
import com.src.taipei_travel.ui.share.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingDetailFragment : Fragment() {
    private val viewModel by viewModels<SettingDetailViewModel>()
    private val shareViewModel by activityViewModels<ShareViewModel>()

    private var binding: FragmentSettingDetailBinding? = null
    private lateinit var settingsAdapter: SettingsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingDetailBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recycleView?.layoutManager = LinearLayoutManager(context)
        settingsAdapter = SettingsAdapter {
            shareViewModel.updateDataStore(it)
        }
        binding?.recycleView?.adapter = settingsAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                shareViewModel.settingDetailState.collect { state ->

                    val toolbar = (activity as AppCompatActivity).supportActionBar
                    toolbar?.title = state.translations[state.settingOption.name]

                    val options: List<Option> = state.settingOption.getSubOptions()
                    settingsAdapter.updateOptions(options)
                    settingsAdapter.updateTranslations(state.translations)
                }
            }
        }
        shareViewModel.settings.observe(viewLifecycleOwner) {
            settingsAdapter.updateSettings(it)
        }
    }
}