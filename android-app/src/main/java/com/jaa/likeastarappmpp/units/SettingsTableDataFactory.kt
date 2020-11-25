package com.jaa.likeastarappmpp.units

import com.jaa.library.feature.settings.model.SettingsRowData
import com.jaa.library.feature.settings.presentation.SettingsTableDataFactoryInterface
import com.jaa.likeastarappmpp.SettingsRow
import dev.icerock.moko.units.TableUnitItem

class SettingsTableDataFactory : SettingsTableDataFactoryInterface {

    override fun createSettingsRow(
        itemId: Long,
        setting: SettingsRowData,
        listener: SettingsTableDataFactoryInterface.OnchangeActiveStateListener
    ): TableUnitItem {
        return SettingsRow().apply {
            this.itemId = itemId
            this.setting = setting
            this.listener = listener
        }
    }

}