import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryUnits

import UIKit

class FilmTableDataFactory: FilmTableDataFactoryInterface {

    func createFilmRow(id: Int64, film: FilmRowData, listener: FilmTableDataFactoryInterfaceListRowTappedListener) -> TableUnitItem {
        return UITableViewCellUnit<FilmTableViewCell>(
            data: FilmTableViewCell.CellModel(
                id: id,
                filmData: film,
                listener: listener
            ),
            itemId: id,
            configurator: nil
        )
    }
    
}
