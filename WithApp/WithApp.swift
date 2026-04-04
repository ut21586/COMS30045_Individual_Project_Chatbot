//
//  WithApp.swift
//  WithApp
//
//  Created for emotional support diabetes management
//  "With you, always."
//

import SwiftUI

enum AppLanguage: String, CaseIterable, Codable {
    case chinese = "zh-Hans"
    case english = "en"
    
    var displayName: String {
        switch self {
        case .chinese: return "简体中文"
        case .english: return "English"
        }
    }
}

@main
struct WithApp: App {
    @StateObject private var appState = AppState()
    @StateObject private var healthManager = HealthManager()
    @StateObject private var chatViewModel = ChatViewModel()
    @StateObject private var reportViewModel = ReportViewModel()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(appState)
                .environmentObject(healthManager)
                .environmentObject(chatViewModel)
                .environmentObject(reportViewModel)
                .preferredColorScheme(.light)
        }
    }
}

// MARK: - App State
class AppState: ObservableObject {
    @Published var isOnboarded: Bool {
        didSet {
            UserDefaults.standard.set(isOnboarded, forKey: "isOnboarded")
        }
    }
    @Published var selectedCharacter: CharacterType {
        didSet {
            UserDefaults.standard.set(selectedCharacter.rawValue, forKey: "selectedCharacter")
        }
    }
    @Published var userName: String {
        didSet {
            UserDefaults.standard.set(userName, forKey: "userName")
        }
    }
    @Published var appLanguage: AppLanguage = .chinese {
        didSet {
            UserDefaults.standard.set(appLanguage.rawValue, forKey: "appLanguage")
        }
    }
    @Published var contextMode: ContextMode = .private
    @Published var notificationsEnabled: Bool = true
    @Published var cgmConnected: Bool = false
    @Published var showVideoAvatar: Bool {
        didSet {
            UserDefaults.standard.set(showVideoAvatar, forKey: "showVideoAvatar")
        }
    }
    
    init() {
        self.isOnboarded = UserDefaults.standard.bool(forKey: "isOnboarded")
        self.userName = UserDefaults.standard.string(forKey: "userName") ?? "User"
        let characterRaw = UserDefaults.standard.string(forKey: "selectedCharacter") ?? CharacterType.robot.rawValue
        self.selectedCharacter = CharacterType(rawValue: characterRaw) ?? .robot
        
        self.showVideoAvatar = UserDefaults.standard.object(forKey: "showVideoAvatar") as? Bool ?? false
        
        if let languageRaw = UserDefaults.standard.string(forKey: "appLanguage"),
           let language = AppLanguage(rawValue: languageRaw) {
            self.appLanguage = language
        } else {
            self.appLanguage = .chinese
        }
    }
}

// MARK: - Character Types
enum CharacterType: String, CaseIterable, Identifiable {
    case robot = "robot"
    case cat = "cat"
    case bear = "bear"
    case bunny = "bunny"
    case panda = "panda"
    
    var id: String { rawValue }
    
    var displayName: String {
        switch self {
        case .robot: return "With"
        case .cat: return "Miao"
        case .bear: return "Bear"
        case .bunny: return "Bunny"
        case .panda: return "Panda"
        }
    }
    
    var emoji: String {
        switch self {
        case .robot: return "🤖"
        case .cat: return "🐱"
        case .bear: return "🐻"
        case .bunny: return "🐰"
        case .panda: return "🐼"
        }
    }
}

// MARK: - Context Mode
enum ContextMode: String, CaseIterable {
    case `private` = "private"
    case `public` = "public"
    case work = "work"
    case social = "social"
    case restaurant = "restaurant"
    
    var displayName: String {
        switch self {
        case .private: return "私密模式"
        case .public: return "公共模式"
        case .work: return "工作模式"
        case .social: return "社交模式"
        case .restaurant: return "就餐模式"
        }
    }
    
    var icon: String {
        switch self {
        case .private: return "house.fill"
        case .public: return "globe"
        case .work: return "briefcase.fill"
        case .social: return "person.3.fill"
        case .restaurant: return "fork.knife"
        }
    }
}
