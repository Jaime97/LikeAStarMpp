package com.jaa.likeastarappmpp.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.jaa.library.feature.filmDetail.model.FilmDetail
import com.jaa.library.feature.filmDetail.presentation.FilmDetailViewModel
import com.jaa.likeastarappmpp.AppComponent
import com.jaa.likeastarappmpp.R
import com.jaa.likeastarappmpp.databinding.ActivityFilmDetailBinding
import com.squareup.picasso.Picasso
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain

class FilmDetailActivity :
    MvvmEventsActivity<ActivityFilmDetailBinding, FilmDetailViewModel, FilmDetailViewModel.EventsListener>(),
    FilmDetailViewModel.EventsListener {

    override val layoutId: Int = R.layout.activity_film_detail
    override val viewModelClass: Class<FilmDetailViewModel> = FilmDetailViewModel::class.java
    override val viewModelVariableId: Int = com.jaa.likeastarappmpp.BR.viewModel

    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {
        AppComponent.factory.filmDetailFactory.createFilmDetailViewModel(
            eventsDispatcher = eventsDispatcherOnMain(),
            getFilmDetailUseCase = AppComponent.factory.getFilmDetailUseCase(),
            changeVisitedStateUseCase = AppComponent.factory.changeVisitedStateUseCase(),
            getFilmImageUseCase = AppComponent.factory.getFilmImageUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onViewCreated()
    }

    override fun getEntryData(key:String): String? {
        return intent.getStringExtra(key)
    }

    override fun loadFilmImage(url: String) {
        Picasso.get()
            .load(url)
            .resize(binding.filmImage.width, binding.filmImage.height)
            .placeholder(R.drawable.placeholder)
            .into(binding.filmImage);
    }

}
