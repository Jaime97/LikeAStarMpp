
import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm

class SettingsViewController: UIViewController {
    
    @IBOutlet var settingsTableView: UITableView!
    private var dataSource: TableUnitsSource!
    private var viewModel: SettingsViewModel!

    override func viewDidLoad() {
        super.viewDidLoad()

        self.viewModel = AppComponent.factory.settingsFactory.createSettingsViewModel(eventsDispatcher: EventsDispatcher(listener: self), getBooleanPreferenceUseCase: AppComponent.factory.getBooleanPreferenceUseCase(), toggleBooleanPreferenceUseCase: AppComponent.factory.toggleBooleanPreferenceUseCase())
        
        self.dataSource = TableUnitsSourceKt.default(for: self.settingsTableView)
        
        self.viewModel.state.data().addObserver { [weak self] itemsObject in
            guard let items = itemsObject as? [TableUnitItem] else { return }
            
            self?.dataSource.unitItems = items
        }
        self.viewModel.onViewCreated()
    }

}
extension SettingsViewController: SettingsViewModelEventsListener {
    
    func getStringFromResource(resource: ResourceStringDesc) -> String {
        return resource.localized()
    }
    
}
