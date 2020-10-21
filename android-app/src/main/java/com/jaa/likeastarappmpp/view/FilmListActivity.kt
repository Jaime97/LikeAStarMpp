
package com.jaa.likeastarappmpp.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.jaa.library.feature.filmList.presentation.FilmListViewModel
import com.jaa.likeastarappmpp.AppComponent
import com.jaa.likeastarappmpp.R
import com.jaa.likeastarappmpp.databinding.ActivityFilmListBinding
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain


class FilmListActivity :
    MvvmEventsActivity<ActivityFilmListBinding, FilmListViewModel, FilmListViewModel.EventsListener>(),
    FilmListViewModel.EventsListener {

    override val layoutId: Int = R.layout.activity_film_list
    override val viewModelClass: Class<FilmListViewModel> = FilmListViewModel::class.java
    override val viewModelVariableId: Int = com.jaa.likeastarappmpp.BR.viewModel

    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {
        AppComponent.factory.filmListFactory.createFilmListViewModel(
            eventsDispatcher = eventsDispatcherOnMain()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onViewCreated()
    }

    override fun addTabToTabLayout(tabText: String, position: Int) {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabText), position)
    }

    override fun configureOnTabSelectedListener(listener: (position: Int) -> Unit) {
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                listener(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    override fun getString(key: String): String {
        val resId = resources.getIdentifier( key,"string", packageName)
        return resources.getString(resId)
    }
}
