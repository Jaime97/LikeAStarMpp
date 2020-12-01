
import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm
import Kingfisher

class FilmDetailViewController: UIViewController {
    
    private var viewModel: FilmDetailViewModel!

    @IBOutlet var filmImageView: UIImageView!
    
    @IBOutlet var titleLabel: UILabel!
    
    @IBOutlet var actorLabel: UILabel!
    
    @IBOutlet var directorLabel: UILabel!
    
    @IBOutlet var producerLabel: UILabel!
    
    @IBOutlet var locationsButton: UIButton!
    
    @IBOutlet var visitedButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.viewModel = AppComponent.factory.filmDetailFactory.createFilmDetailViewModel(eventsDispatcher: EventsDispatcher(listener: self), getFilmDetailUseCase: AppComponent.factory.getFilmDetailUseCase(), changeVisitedStateUseCase: AppComponent.factory.changeVisitedStateUseCase(), getFilmImageUseCase: AppComponent.factory.getFilmImageUseCase())
        // Do any additional setup after loading the view.
        self.viewModel.onViewCreated()
    }

    @IBAction func locationsButtonTapped(_ sender: Any) {
    }
    
    @IBAction func visitedButtonTapped(_ sender: Any) {
    }
    
    deinit {
        // clean viewmodel to stop all coroutines immediately
        self.viewModel.onCleared()
    }
}

extension FilmDetailViewController: FilmDetailViewModelEventsListener {
    func getEntryData(key: String) -> String? {
        return ""
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
    
    func openMapWithLocation(location: String, suffix: StringDesc) {
        
    }
    
    func showListInDialog(title: StringDesc, elementList: KotlinArray<NSString>, onRowTappedListener: @escaping (KotlinInt) -> Void) {
        let alert = UIAlertController(title: title.localized(), message: nil, preferredStyle: .actionSheet)
        var i = 0
        while(i < elementList.size) {
            alert.addAction(UIAlertAction(title: elementList.get(index: Int32(i)) as String?, style: .default , handler:{ (UIAlertAction)in
                onRowTappedListener(KotlinInt(integerLiteral: i))
            }))
            i += 1
        }
        self.present(alert, animated: true, completion: nil)
    }

}
