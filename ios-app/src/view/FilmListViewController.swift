

import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm
import CoreTelephony

class FilmListViewController: UIViewController, UITabBarDelegate {
    
    @IBOutlet var filmTableView: UITableView!

    @IBOutlet var searchTextField: UITextField!
    @IBOutlet var tabBar: UITabBar!
    
    private var dataSource: TableUnitsSource!
    private var viewModel: FilmListViewModel!
    private var searchTextChangedListener: ((String) -> Void)!
    private var endOfTableReachedListener: (() -> Void)!
    private var tabChangedListener: ((KotlinInt) -> Void)?
    private var dataToSendToDetail: [String : String]?
    private var currentNumberOfCells: Int = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.viewModel = AppComponent.factory.filmListFactory.createFilmListViewModel(eventsDispatcher: EventsDispatcher(listener: self), getNextPageInFilmListUseCase: AppComponent.factory.getNextPageInFilmListUseCase(), changeFavouriteStateUseCase: AppComponent.factory.changeFavouriteStateUseCase(), filterByTitleUseCase: AppComponent.factory.filterByTitleUseCase(), filterByFavouriteUseCase: AppComponent.factory.filterByFavouriteUseCase(), getBooleanPreferenceUseCase: AppComponent.factory.getBooleanPreferenceUseCaseForList(), setDownloadOnlyWithWifiUseCase: AppComponent.factory.setDownloadOnlyWithWifiUseCase(), getFilmListUseCase: AppComponent.factory.getFilmListUseCase())
        
        self.dataSource = TableUnitsSourceKt.default(for: self.filmTableView)
        
        self.viewModel.state.data().addObserver { [weak self] itemsObject in
            let items = ((itemsObject as? [TableUnitItem]) != nil) ? itemsObject as! [TableUnitItem] : [TableUnitItem]()
            self?.currentNumberOfCells = items.count
            self?.dataSource.unitItems = items
            self?.filmTableView.reloadData()
        }
        
        self.searchTextField.placeholder = self.viewModel.getSearchString().localized()
        UITabBarItem.appearance().setTitleTextAttributes([NSAttributedString.Key.font: UIFont(name: "ArialRoundedMTBold", size: 14)!], for: .normal)
        UITabBarItem.appearance().setTitleTextAttributes([NSAttributedString.Key.font: UIFont(name: "ArialRoundedMTBold", size: 14)!], for: .selected)
        self.tabBar.delegate = self
        self.viewModel.onViewCreated()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.viewModel.onViewPresented()
    }
    
    @IBAction func settingsButtonTapped(_ sender: Any) {
        self.viewModel.onSettingsButtonPressed()
    }
    
    func tabBar(_ tabBar: UITabBar, didSelect item: UITabBarItem) {
        let index = self.tabBar.items?.firstIndex(of: item)
        self.tabChangedListener?(KotlinInt(integerLiteral: index!))
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if(segue.identifier == "showDetail") {
            let filmDetailViewController = segue.destination as! FilmDetailViewController
            filmDetailViewController.receivedData = self.dataToSendToDetail
        }
    }
    
    @objc func searchTextFieldDidChange(_ textField: UITextField) {
        self.searchTextChangedListener(self.searchTextField.text ?? "")
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.viewModel.onViewWillDisappear()
        super.viewWillDisappear(animated)
    }

}

extension FilmListViewController: FilmListViewModelEventsListener {
    func addOnEndOfListReachedListener(listener: @escaping () -> Void) {
        self.filmTableView.delegate = self
        self.endOfTableReachedListener = listener
    }
    
    func addOnTabLayoutChangedListener(listener: @escaping (KotlinInt) -> Void) {
        self.tabChangedListener = listener
    }
    
    func addTabToTabLayout(tabText: StringDesc, position: Int32) {
        var items = self.tabBar.items ?? [UITabBarItem]()
        let item = UITabBarItem(title: tabText.localized(), image: nil, tag: Int(position))
        item.titlePositionAdjustment = UIOffset(horizontal: 0, vertical: -16)
        items.append(item)
        
        self.tabBar.setItems(items, animated: true)
        if((self.tabBar.selectedItem) == nil) {
            tabBar.selectedItem = tabBar.items![0] as UITabBarItem
        }
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
        self.dataToSendToDetail = data
        performSegue(withIdentifier: "showDetail", sender: nil)
    }
    
    func presentSettingsView() {
        performSegue(withIdentifier: "showSettings", sender: nil)
    }
    
    func setOnSearchBarTextChangedListener(listener: @escaping (String) -> Void) {
        self.searchTextChangedListener = listener
        self.searchTextField.addTarget(self, action: #selector(searchTextFieldDidChange(_:)), for: .editingChanged)
    }
}

extension FilmListViewController: UITableViewDelegate {

    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        if indexPath.row + 1 == self.currentNumberOfCells {
            self.endOfTableReachedListener?()
        }
    }
    
}
