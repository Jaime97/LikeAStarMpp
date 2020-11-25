package com.jaa.library.feature.settings.presentation

import com.jaa.library.feature.settings.model.SettingsRowData
import dev.icerock.moko.units.TableUnitItem

interface SettingsTableDataFactoryInterface {

    fun createSettingsRow(itemId: Long, setting: SettingsRowData, listener: OnchangeActiveStateListener): TableUnitItem

    interface OnchangeActiveStateListener {
        fun onChange(settingKey:String)
    }
}