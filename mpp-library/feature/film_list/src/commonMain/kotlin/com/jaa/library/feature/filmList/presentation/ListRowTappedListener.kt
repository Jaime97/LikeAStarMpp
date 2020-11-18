package com.jaa.library.feature.filmList.presentation

interface ListRowTappedListener {
    fun onRowTapped(title:String)
    fun onFavouriteButtonTapped(title:String)
}