/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary

@UIApplicationMain
class AppDelegate: NSObject, UIApplicationDelegate {
    
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {
        AppComponent.factory = SharedFactory(
            antilog: DebugAntilog(defaultTag: "MPP"),
            baseFilmUrl: "https://data.sfgov.org",
            baseFilmImageUrl: "https://www.omdbapi.com",
            filmTableDataFactory: FilmTableDataFactory(),
            settingsTableDataFactory: SettingsTableDataFactory(),
            databaseDriverFactory: DatabaseDriverFactory()
        )
        return true
    }
}
