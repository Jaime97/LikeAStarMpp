
import UIKit
import MultiPlatformLibraryUnits
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm


class FilmTableViewCell: UITableViewCell, Fillable {
    
    typealias DataType = CellModel
    
    
    @IBOutlet var titleLabel: UILabel!
    
    @IBOutlet var directorLabel: UILabel!
    
    @IBOutlet var favouriteButton: UIButton!
    
    @IBOutlet var visitedImage: UIImageView!
    var listener:FilmTableDataFactoryInterfaceListRowTappedListener!
    
    struct CellModel {
        let id: Int64
        let filmData: FilmRowData
        let listener: FilmTableDataFactoryInterfaceListRowTappedListener
    }
    
    func fill(_ data: CellModel) {
        self.listener = data.listener
        self.titleLabel.text = data.filmData.title
        self.directorLabel.text = data.filmData.director
        self.favouriteButton.setImage(data.filmData.favourite ? UIImage(named: "star_selected"):UIImage(named: "star_unselected"), for: .normal)
        self.favouriteButton.imageView?.setNeedsLayout()
        self.visitedImage.alpha = data.filmData.visited ? 1.0:0.0
    }
    
    @IBAction func onFavouriteButtonTapped(_ sender: Any) {
        self.listener.onFavouriteButtonTapped(title: self.titleLabel.text ?? "")
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.listener.onRowTapped(title: self.titleLabel.text ?? "")
    }
}

extension FilmTableViewCell: Reusable {
    static func reusableIdentifier() -> String {
        return "FilmTableViewCell"
    }
    
    static func xibName() -> String {
        return "FilmTableViewCell"
    }
    
    static func bundle() -> Bundle {
        return Bundle.main
    }
}
