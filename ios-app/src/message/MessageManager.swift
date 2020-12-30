
import UIKit
import MultiPlatformLibrary

class MessageManager {
    
    func showErrorMessage(text: String, viewController : UIViewController) {
        let alertDisapperTimeInSeconds = 2.0
        let alert = UIAlertController(title: nil, message: text, preferredStyle: .actionSheet)
        viewController.present(alert, animated: true)
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + alertDisapperTimeInSeconds) {
          alert.dismiss(animated: true)
        }
    }
    
    func showAlert(title: StringDesc, description: StringDesc, buttonTitle: StringDesc, viewController : UIViewController, onButtonPressed: (() -> Void)? = nil) {
        let alert = UIAlertController(title:title.localized(), message: description.localized(), preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: buttonTitle.localized(), style: .default, handler: { alertAction in
            onButtonPressed?()
        }))
        viewController.present(alert, animated: true)
    }
    
    func getStringFromResource(resource: ResourceStringDesc) -> String {
        return resource.localized()
    }

}
