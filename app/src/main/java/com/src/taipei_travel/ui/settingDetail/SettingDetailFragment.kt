package com.src.taipei_travel.ui.settingDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.src.taipei_travel.data.model.DarkMode
import com.src.taipei_travel.data.model.Language
import com.src.taipei_travel.databinding.FragmentSettingDetailBinding
import com.src.taipei_travel.ui.setting.SettingsAdapter
import com.src.taipei_travel.data.model.Option
import com.src.taipei_travel.data.model.Setting
import com.src.taipei_travel.data.model.toOption
import com.src.taipei_travel.data.model.toSetting
import com.src.taipei_travel.ui.share.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingDetailFragment : Fragment() {
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

        val optionString = arguments?.getString("argJsonString")
        val option: Option = Gson().fromJson(optionString, Option::class.java)
        val setting: Setting = option.toSetting() ?: return
        val options = when(setting) {
            Setting.Language -> Language.entries.map { it.toOption() }
            Setting.DarkMode -> DarkMode.entries.map { it.toOption() }
        }

        binding?.recycleView?.layoutManager = LinearLayoutManager(context)
        settingsAdapter = SettingsAdapter(options) {
            shareViewModel.updateDataStore(setting, it)
        }
        binding?.recycleView?.adapter = settingsAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                shareViewModel.translations.collect { state ->
                    val toolbar = (activity as AppCompatActivity).supportActionBar
                    toolbar?.title = state[setting.name]
                    settingsAdapter.updateTranslations(state)
                }
            }
        }
        shareViewModel.settings.observe(viewLifecycleOwner) { settings ->
            val selectedOptionIndex = when(setting) {
                Setting.Language -> Language.entries.indexOf(settings.language)
                Setting.DarkMode -> DarkMode.entries.indexOf(settings.darkMode)
            }
            settingsAdapter.updateSelectedIndex(selectedOptionIndex)
        }
    }
}