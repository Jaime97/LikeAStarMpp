
import UIKit
import MultiPlatformLibraryUnits
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm

class SettingsTableDataFactory: SettingsTableDataFactoryInterface {
    
    func createSettingsRow(itemId: Int64, setting: SettingsRowData, listener: SettingsTableDataFactoryInterfaceOnchangeActiveStateListener) -> TableUnitItem {
        return UITableViewCellUnit<SettingsTableViewCell>(
            data: SettingsTableViewCell.CellModel(
                settingData: setting,
                listener: listener
            ),
            itemId: itemId,
            configurator: nil
        )
    }

}
