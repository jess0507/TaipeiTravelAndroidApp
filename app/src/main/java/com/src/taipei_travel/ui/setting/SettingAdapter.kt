package com.src.taipei_travel.ui.setting
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.src.taipei_travel.R
import com.src.taipei_travel.data.local.datastore.model.Settings
import com.src.taipei_travel.ui.settingDetail.model.DarkModeOption
import com.src.taipei_travel.ui.settingDetail.model.LanguageOption
import com.src.taipei_travel.ui.settingDetail.model.Option
import com.src.taipei_travel.ui.settingDetail.model.SettingOption

class SettingsAdapter(
    private var options: List<Option> = listOf(),
    private val showCheck: Boolean = true,
    private var translation: Map<String, String> = emptyMap(),
    private val itemClickListener: (Option) -> Unit,
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)
        return SettingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val setting = options[position]
        holder.settingName.text = translation[setting.name]
        if (showCheck) {
            holder.checkMark.visibility =
                if (position == selectedPosition) View.VISIBLE else View.GONE
        }
        holder.itemView.setOnClickListener {
            itemClickListener(setting)
        }
    }

    override fun getItemCount() = options.size

    fun updateOptions(options: List<Option>) {
        this.options = options
    }

    fun updateSettings(settings: Settings) {
        if (!showCheck) return

        val index: Int = options.indexOfFirst {
            when(it) {
                is DarkModeOption -> {
                    it.id == settings.darkMode
                }
                is LanguageOption -> {
                    it.language == settings.language
                }
                else -> false
            }
        }

        val lastIndex = selectedPosition
        selectedPosition = index
        notifyItemChanged(lastIndex)
        notifyItemChanged(index)
    }

    fun updateTranslations(trans: Map<String, String>) {
        translation = trans
        notifyDataSetChanged()
    }

    class SettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val settingName: TextView = itemView.findViewById(R.id.setting_name)
        val checkMark: ImageView = itemView.findViewById(R.id.check_mark)
    }
}
