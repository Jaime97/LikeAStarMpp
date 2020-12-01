import UIKit
import MultiPlatformLibraryUnits
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm

class SettingsTableViewCell: UITableViewCell, Fillable {
    
    @IBOutlet var descriptionLabel: UILabel!
    
    @IBOutlet var settingSwitch: UISwitch!
    
    @IBOutlet var titleLabel: UILabel!
    
    var listener:SettingsTableDataFactoryInterfaceOnchangeActiveStateListener!
    var settingKey:String!
    
    typealias DataType = CellModel
    
    struct CellModel {
        let settingData: SettingsRowData
        let listener: SettingsTableDataFactoryInterfaceOnchangeActiveStateListener
    }
    
    func fill(_ data: CellModel) {
        self.settingKey = data.settingData.key
        self.listener = data.listener
        self.titleLabel.text = data.settingData.titleText
        self.descriptionLabel.text = data.settingData.descriptionText
    }
    
    @IBAction func onSwitchChanged(_ sender: Any) {
        self.listener.onChange(settingKey: self.settingKey)
    }
}

extension SettingsTableViewCell: Reusable {
    static func reusableIdentifier() -> String {
        return "SettingsTableViewCell"
    }
    
    static func xibName() -> String {
        return "SettingsTableViewCell"
    }
    
    static func bundle() -> Bundle {
        return Bundle.main
    }
}
