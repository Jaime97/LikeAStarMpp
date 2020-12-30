package com.jaa.library.domain.preferences

interface PreferenceManagerInterface {

    fun putBoolean(key:String, value:Boolean)

    fun getBoolean(key:String, defaultValue:Boolean):Boolean

}