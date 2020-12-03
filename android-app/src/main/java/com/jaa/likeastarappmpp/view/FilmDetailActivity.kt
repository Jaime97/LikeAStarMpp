package com.jaa.likeastarappmpp.view

import android.app.AlertDialog
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.jaa.library.feature.filmDetail.presentation.FilmDetailViewModel
import com.jaa.likeastarappmpp.AppComponent
import com.jaa.likeastarappmpp.R
import com.jaa.likeastarappmpp.databinding.ActivityFilmDetailBinding
import com.jaa.likeastarappmpp.location.AppLocationProvider
import com.squareup.picasso.Picasso
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.resources.desc.StringDesc


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
            getFilmImageUseCase = AppComponent.factory.getFilmImageUseCase(),
            permissionsController = PermissionsController(applicationContext = this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.permissionsController.bind(lifecycle, supportFragmentManager)
        viewModel.onViewCreated()
    }

    override fun getEntryData(key: String): String? {
        return intent.getStringExtra(key)
    }

    override fun loadFilmImage(url: String) {
        Picasso.get()
            .load(url)
            .resize(binding.filmImage.width, binding.filmImage.height)
            .placeholder(R.drawable.placeholder)
            .into(binding.filmImage)
    }

    override fun showListInDialog(
        title: StringDesc,
        elementList: Array<String>,
        onRowTappedListener: (position: Int) -> Unit
    ) {
        val builder = AlertDialog.Builder(this, R.style.ListAlertDialog)
        builder.setTitle(title.toString(this))
        builder.setItems(elementList) { _, which ->
            onRowTappedListener(which)
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun openMapWithLocation(
        originCoordinates: Pair<Double, Double>,
        destinyLocation: String,
        suffix: StringDesc
    ) {
        val intent = Intent(
            Intent.ACTION_VIEW,

            Uri.parse("http://maps.google.com/maps?saddr=" + originCoordinates.first
                    +"," + originCoordinates.second +  "&daddr=$destinyLocation" + suffix.toString(this))
        )
        startActivity(intent)
    }

    override fun showAlert(title: StringDesc, description: StringDesc, buttonTitle: StringDesc) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title.toString(this))
        builder.setMessage(description.toString(this))
        builder.setPositiveButton(buttonTitle.toString(this), null)
        builder.show()
    }

    override fun geUserLocation(onSuccessListener:(latitude: Double, longitude: Double) -> Unit) {
        AppLocationProvider().getLocation(this, object : AppLocationProvider.LocationCallBack {
            override fun locationResult(location: Location?) {
                onSuccessListener(location!!.latitude, location.longitude)
            }
        })
    }

}
