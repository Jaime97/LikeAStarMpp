
import UIKit
import MultiPlatformLibraryUnits
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm

class SettingsTableDataFactory: SettingsTableDataFactoryInterface {
    
    func createSettingsRow(itemId: Int64, setting: SettingsRowData, listener: SettingsTableDataFactoryInterfaceOnchangeActiveStateListener) -> TableUnitItem {
        return UITableViewCellUnit<SettingsRowTableViewCell>(
            data: SettingsRowTableViewCell.CellModel(
                settingData: setting,
                listener: listener
            ),
            itemId: itemId,
            configurator: nil
        )
    }

}
