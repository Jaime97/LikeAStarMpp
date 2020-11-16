
package com.jaa.likeastarappmpp.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.jaa.library.feature.filmList.presentation.FilmListViewModel
import com.jaa.likeastarappmpp.AppComponent
import com.jaa.likeastarappmpp.R
import com.jaa.likeastarappmpp.databinding.ActivityFilmListBinding
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.resources.desc.StringDesc


class FilmListActivity :
    MvvmEventsActivity<ActivityFilmListBinding, FilmListViewModel, FilmListViewModel.EventsListener>(),
    FilmListViewModel.EventsListener {

    override val layoutId: Int = R.layout.activity_film_list
    override val viewModelClass: Class<FilmListViewModel> = FilmListViewModel::class.java
    override val viewModelVariableId: Int = com.jaa.likeastarappmpp.BR.viewModel

    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {
        AppComponent.factory.filmListFactory.createFilmListViewModel(
            eventsDispatcher = eventsDispatcherOnMain(),
            getNextPageInFilmListUseCase = AppComponent.factory.getNextPageInFilmListUseCase(),
            changeFavouriteStateUseCase = AppComponent.factory.changeFavouriteStateUseCase(),
            filterByFavouriteUseCase = AppComponent.factory.filterByFavouriteUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onViewCreated()
    }

    override fun addTabToTabLayout(tabText: StringDesc, position: Int) {
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText(tabText.toString(this)),
            position
        )
    }

    override fun addOnTabLayoutChangedListener(listener: (position: Int) -> Unit) {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                listener(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Without functionality
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Without functionality
            }

        })
    }

    override fun addOnEndOfListReachedListener(listener: () -> Unit) {
        binding.filmListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    listener()
                }
            }
        })
    }

    override fun setOnSearchBarTextChangedListener(listener: (text: String) -> Unit) {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Without functionality
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener(s.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                // Without functionality
            }

        })
    }
}
