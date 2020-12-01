
import UIKit
import MultiPlatformLibraryUnits
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm


class FilmListRowTableViewCell: UITableViewCell, Fillable {
    
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var directorLabel: UILabel!
    @IBOutlet var favouriteButton: UIButton!
    @IBOutlet var visitedImage: UIImageView!
    var listener:FilmTableDataFactoryInterfaceListRowTappedListener!
    
    typealias DataType = CellModel
    
    struct CellModel {
        let id: Int64
        let filmData: FilmRowData
        let listener: FilmTableDataFactoryInterfaceListRowTappedListener
    }
    
    func fill(_ data: CellModel) {
        self.listener = data.listener
        self.titleLabel.text = data.filmData.title
        self.directorLabel.text = data.filmData.director
        self.favouriteButton.imageView?.image = data.filmData.favourite ? UIImage(named: "star_selected"):UIImage(named: "star_unselected")
        self.visitedImage.alpha = data.filmData.visited ? 1.0:0.0
    }
    
    @IBAction func onFavouriteButtonTapped(_ sender: Any) {
        self.listener.onFavouriteButtonTapped(title: self.titleLabel.text ?? "")
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.listener.onRowTapped(title: self.titleLabel.text ?? "")
    }
}

extension FilmListRowTableViewCell: Reusable {
    static func reusableIdentifier() -> String {
        return "FilmListRowTableViewCell"
    }
    
    static func xibName() -> String {
        return "FilmListRowTableViewCell"
    }
    
    static func bundle() -> Bundle {
        return Bundle.main
    }
}
