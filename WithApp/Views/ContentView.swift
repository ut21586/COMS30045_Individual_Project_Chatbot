//
//  ContentView.swift
//  WithApp
//
//  Main content view with tab navigation
//

import SwiftUI
import Foundation
#if canImport(UIKit)
import UIKit
#endif

struct ContentView: View {
    @EnvironmentObject var appState: AppState
    @State private var selectedTab: Tab = .home
    @State private var showProfile = false
    
    enum Tab {
        case home
        case chat
        case report
        case settings
        
        func localizedTitle(for language: AppLanguage) -> String {
            switch (self, language) {
            case (.home, .chinese): return "主页"
            case (.chat, .chinese): return "对话"
            case (.report, .chinese): return "报告"
            case (.settings, .chinese): return "设置"
            case (.home, .english): return "Home"
            case (.chat, .english): return "Chat"
            case (.report, .english): return "Report"
            case (.settings, .english): return "Settings"
            }
        }
    }
    
    // Detect Xcode preview environment at runtime to avoid initializing heavy views
    private var isRunningInPreviews: Bool {
        ProcessInfo.processInfo.environment["XCODE_RUNNING_FOR_PREVIEWS"] == "1"
    }
    
    // Safe gradient colors with UIKit fallback to avoid type inference issues
    private func gradientColors() -> [Color] {
        #if canImport(UIKit)
        let top = UIColor(named: "BackgroundTop") ?? UIColor.systemGroupedBackground
        let bottom = UIColor(named: "BackgroundBottom") ?? UIColor.secondarySystemGroupedBackground
        return [Color(top), Color(bottom)]
        #else
        return [Color.gray.opacity(0.98), Color.gray.opacity(0.94)]
        #endif
    }
    
    var body: some View {
        Group {
            if !appState.isOnboarded {
                OnboardingView()
            } else {
                mainContent
            }
        }
        .preferredColorScheme(.light)
    }
    
    private var mainContent: some View {
        ZStack {
            // Background gradient
            LinearGradient(
                gradient: Gradient(colors: gradientColors()),
                startPoint: .top,
                endPoint: .bottom
            )
            .ignoresSafeArea()
            
            VStack(spacing: 0) {
                // Content
                TabView(selection: $selectedTab) {
                    HomeView(showProfile: $showProfile)
                        .tag(Tab.home)
                        .accessibilityLabel(ContentView.Tab.home.localizedTitle(for: appState.appLanguage))
                    
                    ChatView()
                        .tag(Tab.chat)
                        .accessibilityLabel(ContentView.Tab.chat.localizedTitle(for: appState.appLanguage))
                    
                    ReportView()
                        .tag(Tab.report)
                        .accessibilityLabel(ContentView.Tab.report.localizedTitle(for: appState.appLanguage))
                    
                    SettingsView()
                        .tag(Tab.settings)
                        .accessibilityLabel(ContentView.Tab.settings.localizedTitle(for: appState.appLanguage))
                }
                .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
                .onAppear {
                    if isRunningInPreviews {
                        selectedTab = .home
                    }
                }
                
                // Custom Tab Bar
                CustomTabBar(selectedTab: $selectedTab)
            }
        }
        .sheet(isPresented: $showProfile) {
            ProfileView()
        }
    }
}

// MARK: - Custom Tab Bar
struct CustomTabBar: View {
    @Binding var selectedTab: ContentView.Tab
    @EnvironmentObject var appState: AppState
    
    var body: some View {
        HStack(spacing: 0) {
            TabBarButton(
                icon: "house.fill",
                title: ContentView.Tab.home.localizedTitle(for: appState.appLanguage),
                isSelected: selectedTab == .home
            ) {
                selectedTab = .home
            }
            
            TabBarButton(
                icon: "message.fill",
                title: ContentView.Tab.chat.localizedTitle(for: appState.appLanguage),
                isSelected: selectedTab == .chat
            ) {
                selectedTab = .chat
            }
            
            TabBarButton(
                icon: "chart.bar.fill",
                title: ContentView.Tab.report.localizedTitle(for: appState.appLanguage),
                isSelected: selectedTab == .report
            ) {
                selectedTab = .report
            }
            
            TabBarButton(
                icon: "gearshape.fill",
                title: ContentView.Tab.settings.localizedTitle(for: appState.appLanguage),
                isSelected: selectedTab == .settings
            ) {
                selectedTab = .settings
            }
        }
        .padding(.horizontal, 20)
        .padding(.vertical, 12)
        .background(
            RoundedRectangle(cornerRadius: 25)
                .fill(.ultraThinMaterial)
                .shadow(color: .black.opacity(0.1), radius: 10, y: -5)
        )
        .padding(.horizontal, 20)
        .padding(.bottom, 10)
    }
}

struct TabBarButton: View {
    let icon: String
    let title: String
    let isSelected: Bool
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            VStack(spacing: 4) {
                Image(systemName: icon)
                    .font(.system(size: 22))
                    .foregroundColor(isSelected ? Color("AccentTeal") : .gray)
                
                Text(title)
                    .font(.caption2)
                    .foregroundColor(isSelected ? Color("AccentTeal") : .gray)
            }
            .frame(maxWidth: .infinity)
        }
    }
}

struct PreviewSafePlaceholder: View {
    let title: String
    let systemImage: String
    var body: some View {
        VStack(spacing: 16) {
            Image(systemName: systemImage)
                .font(.system(size: 28))
                .foregroundColor(Color("AccentTeal"))
            Text(title)
                .font(.system(size: 22, weight: .semibold))
            RoundedRectangle(cornerRadius: 18)
                .fill(Color.white)
                .frame(height: 180)
                .shadow(color: .black.opacity(0.06), radius: 10, y: 6)
                .overlay(Text("Preview Placeholder").foregroundColor(.gray))
                .padding(.horizontal, 24)
            Spacer()
        }
        .padding(.top, 40)
    }
}

#if canImport(UIKit)
extension Color {
    init(namedOrSystem name: String, fallback: Color) {
        if let uiColor = UIColor(named: name) {
            self = Color(uiColor: uiColor)
        } else {
            self = fallback
        }
    }
}
#endif

#if DEBUG
#Preview("ContentView - Full") {
    let appState = AppState()
    appState.appLanguage = .chinese
    appState.isOnboarded = true
    let healthManager = HealthManager()
    let chatVM = ChatViewModel()
    let reportVM = ReportViewModel()

    return ContentView()
        .environmentObject(appState)
        .environmentObject(healthManager)
        .environmentObject(chatVM)
        .environmentObject(reportVM)
        .environment(\.locale, Locale(identifier: "zh-Hans"))
}

#Preview("ContentView - Onboarding") {
    let appState = AppState()
    appState.appLanguage = .chinese
    appState.isOnboarded = false
    return ContentView()
        .environmentObject(appState)
        .environmentObject(HealthManager())
        .environmentObject(ChatViewModel())
        .environmentObject(ReportViewModel())
        .environment(\.locale, Locale(identifier: "zh-Hans"))
}
#endif

