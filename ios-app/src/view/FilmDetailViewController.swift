
import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm
import Kingfisher
import CoreLocation

class FilmDetailViewController: UIViewController {
    
    private var viewModel: FilmDetailViewModel!

    @IBOutlet var filmImageView: UIImageView!
    
    @IBOutlet var titleLabel: UILabel!
    
    @IBOutlet var actorLabel: UILabel!
    
    @IBOutlet var directorLabel: UILabel!
    
    @IBOutlet var producerLabel: UILabel!
    
    @IBOutlet var locationsButton: UIButton!
    
    @IBOutlet var visitedButton: UIButton!
    
    var receivedData: [String : String]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.viewModel = AppComponent.factory.filmDetailFactory.createFilmDetailViewModel(eventsDispatcher: EventsDispatcher(listener: self), getFilmDetailUseCase: AppComponent.factory.getFilmDetailUseCase(), changeVisitedStateUseCase: AppComponent.factory.changeVisitedStateUseCase(), getFilmImageUseCase: AppComponent.factory.getFilmImageUseCase(), permissionsController: PermissionsPermissionsController())
        // Do any additional setup after loading the view.
        self.locationsButton.setTitle(self.viewModel.getSeeLocationsString().localized(), for: .normal)
        
        self.viewModel.state.data().addObserver { [weak self] itemObject in
            guard let filmDetail = itemObject as? FilmDetail else { return }
            self?.titleLabel.text = filmDetail.title
            self?.actorLabel.text = String(format: (self?.viewModel.getStarringByString().localized().replacingOccurrences(of: "%s", with: "%@"))!, filmDetail.actor1)
            self?.directorLabel.text = String(format: (self?.viewModel.getDirectedByString().localized().replacingOccurrences(of: "%s", with: "%@"))!, filmDetail.director)
            self?.producerLabel.text = String(format: (self?.viewModel.getProducedByString().localized().replacingOccurrences(of: "%s", with: "%@"))!, filmDetail.productionCompany)
            self?.visitedButton.setTitle(filmDetail.visited ? self?.viewModel.getVisitedButtonString().localized() :
                self?.viewModel.getUnvisitedButtonString().localized(), for: .normal)
        }
        
        self.viewModel.onViewCreated()
    }

    @IBAction func locationsButtonTapped(_ sender: Any) {
        self.viewModel.onLocationsButtonTapped()
    }
    
    @IBAction func visitedButtonTapped(_ sender: Any) {
        self.viewModel.onChangeVisitedStateButtonTapped()
    }
    
    deinit {
        // clean viewmodel to stop all coroutines immediately
        self.viewModel.onCleared()
    }
}

extension FilmDetailViewController: FilmDetailViewModelEventsListener {
    func geUserLocation(onSuccessListener: @escaping (KotlinDouble, KotlinDouble) -> Void) {
        let locationManager = CLLocationManager()
        let currentLoc: CLLocation! = locationManager.location
        onSuccessListener(KotlinDouble(value: currentLoc.coordinate.latitude),  KotlinDouble(value: currentLoc.coordinate.longitude))
    }
    
    func openMapWithLocation(originCoordinates: KotlinPair<KotlinDouble, KotlinDouble>, destinyLocation: String, suffix: StringDesc) {
        let originAddress = "saddr=" + String(originCoordinates.first! as! Double) + "," + String(originCoordinates.second! as! Double)
        let destinyAddress = "daddr=" + destinyLocation + suffix.localized()
        let urlString = "http://maps.google.com/maps?" + originAddress + "&" + destinyAddress
        UIApplication.shared.open(URL(string:urlString.replacingOccurrences(of: " ", with: "+").folding(options: .diacriticInsensitive, locale: .current))!, options:[:], completionHandler: nil)
    }
    
    func showAlert(title: StringDesc, description: StringDesc, buttonTitle: StringDesc) {
        let alert = UIAlertController(title:title.localized(), message: description.localized(), preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: buttonTitle.localized(), style: .default, handler: nil))
        self.present(alert, animated: true)
    }
    
    func getEntryData(key: String) -> String? {
        return receivedData?[key] ?? ""
    }
    
    func loadFilmImage(url: String) {
        let processor = DownsamplingImageProcessor(size: self.filmImageView.bounds.size)
        self.filmImageView.kf.indicatorType = .activity
        self.filmImageView.kf.setImage(
            with: URL(string: url),
            placeholder: UIImage(named: "placeholder"),
            options:[
                .processor(processor),
                .scaleFactor(UIScreen.main.scale),
                .transition(.fade(1)),
                .cacheOriginalImage
            ] as KingfisherOptionsInfo)
        {
            result in
            switch result {
            case .success(let value):
                print("Task done for: \(value.source.url?.absoluteString ?? "")")
            case .failure(let error):
                print("Job failed: \(error.localizedDescription)")
            }
        }
    }
    
    func showListInDialog(title: StringDesc, elementList: KotlinArray<NSString>, onRowTappedListener: @escaping (KotlinInt) -> Void) {
        let alert = UIAlertController(title: title.localized(), message: nil, preferredStyle: .actionSheet)
        var i = 0
        while(i < elementList.size) {
            alert.addAction(UIAlertAction(title: elementList.get(index: Int32(i)) as String?, style: .default , handler:{ action in
                if let index = alert.actions.firstIndex(where: { $0 === action }) {
                     onRowTappedListener(KotlinInt(integerLiteral: index))
                }
            }))
            i += 1
        }
        self.present(alert, animated: true, completion: nil)
    }

}
