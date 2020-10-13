/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import Foundation
import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm
import SkyFloatingLabelTextField

class FilmListViewController: UIViewController {
    
    private var viewModel: FilmListViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel = AppComponent.factory.filmListFactory.createFilmListViewModel(eventsDispatcher: EventsDispatcher(listener: self))

    }
    
    @IBAction func onSubmitPressed() {
        viewModel.onSubmitPressed()
    }
    
    deinit {
        // clean viewmodel to stop all coroutines immediately
        viewModel.onCleared()
    }
}

extension FilmListViewController: FilmListViewModelEventsListener {

}
