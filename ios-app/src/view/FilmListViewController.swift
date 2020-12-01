

import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm
import CoreTelephony

class FilmListViewController: UIViewController {
    
    @IBOutlet var filmTableView: UITableView!

    @IBOutlet var searchTextField: UITextField!
    @IBOutlet var tabBar: UITabBar!
    
    private var dataSource: TableUnitsSource!
    private var viewModel: FilmListViewModel!
    private var searchTextChangedListener: ((String) -> Void)!
    private var endOfTableReachedListener: (() -> Void)!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.viewModel = AppComponent.factory.filmListFactory.createFilmListViewModel(eventsDispatcher: EventsDispatcher(listener: self), getNextPageInFilmListUseCase: AppComponent.factory.getNextPageInFilmListUseCase(), changeFavouriteStateUseCase: AppComponent.factory.changeFavouriteStateUseCase(), filterByTitleUseCase: AppComponent.factory.filterByTitleUseCase(), filterByFavouriteUseCase: AppComponent.factory.filterByFavouriteUseCase(), getBooleanPreferenceUseCase: AppComponent.factory.getBooleanPreferenceUseCaseForList(), setDownloadOnlyWithWifiUseCase: AppComponent.factory.setDownloadOnlyWithWifiUseCase(), getFilmListUseCase: AppComponent.factory.getFilmListUseCase())
        
        self.dataSource = TableUnitsSourceKt.default(for: self.filmTableView)
        
        self.viewModel.state.data().addObserver { [weak self] itemsObject in
            guard let items = itemsObject as? [TableUnitItem] else { return }
            
            self?.dataSource.unitItems = items
            self?.filmTableView.reloadData()
            self?.filmTableView.reloadInputViews()
        }
        self.viewModel.onViewCreated()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.viewModel.onViewPresented()
    }
    
    @IBAction func settingsButtonTapped(_ sender: Any) {
        self.viewModel.onSettingsButtonPressed()
    }
    
    @objc func searchTextFieldDidChange(_ textField: UITextField) {
        self.searchTextChangedListener(self.searchTextField.text ?? "")
    }
    
    @objc func onTableRefresh() {
        endOfTableReachedListener()
    }
    
    deinit {
        // clean viewmodel to stop all coroutines immediately
        self.viewModel.onCleared()
    }
}

extension FilmListViewController: FilmListViewModelEventsListener {
    func addOnEndOfListReachedListener(listener: @escaping () -> Void) {
        endOfTableReachedListener = listener
    }
    
    func addOnTabLayoutChangedListener(listener: @escaping (KotlinInt) -> Void) {
    }
    
    func addTabToTabLayout(tabText: StringDesc, position: Int32) {
        var items = self.tabBar.items ?? [UITabBarItem]()
        let item = UITabBarItem(title: tabText.localized(), image: nil, tag: Int(position))
        items.append(UITabBarItem(title: tabText.localized(), image: nil, tag: Int(position)))
        
        self.tabBar.setItems(items, animated: true)
    }
    
    func isWifiActive() -> Bool {
        var isWifiActive = false
        if let reachability = Reachability.forInternetConnection() {
            reachability.startNotifier()
            let status = reachability.currentReachabilityStatus()
            if status == .init(1) {
                // .ReachableViaWiFi
                isWifiActive = true

            }
        }
        return isWifiActive
    }
    
    func presentFilmDetailView(data: [String : String]) {
        
    }
    
    func presentSettingsView() {
        
    }
    
    func setOnSearchBarTextChangedListener(listener: @escaping (String) -> Void) {
        self.searchTextChangedListener = listener
        self.searchTextField.addTarget(self, action: #selector(searchTextFieldDidChange(_:)), for: .editingChanged)
    }

}
