//
//  HomeView.swift
//  WithApp
//
//  Main home screen with character interaction and supportive messages
//

import SwiftUI

struct HomeView: View {
    @EnvironmentObject var appState: AppState
    @EnvironmentObject var healthManager: HealthManager
    @EnvironmentObject var chatViewModel: ChatViewModel
    @Binding var selectedTab: ContentView.Tab
    @Binding var showSettings: Bool
    
    @State private var currentMessage: SupportMessage = SupportMessage.defaultMessage
    @State private var showInputField = false
    @State private var showStoryCard = true
    @State private var animateCharacter = false
    
    var body: some View {
        ZStack {
            // Background
            backgroundGradient
            
            VStack(spacing: 0) {
                // Header
                headerView
                
                Spacer()
                
                // Character and Message Area
                characterSection
                
                Spacer()
                
                // Story Card
                if showStoryCard {
                    storyCard
                }
                
                // Quick Input Suggestions
                quickInputSuggestions
                
                Spacer().frame(height: 20)
            }
        }
        .onAppear {
            updateSupportMessage()
        }
        .onChange(of: healthManager.latestGlucose) { _, _ in
            updateSupportMessage()
        }
    }
    
    // MARK: - Background
    private var backgroundGradient: some View {
        LinearGradient(
            colors: [
                Color(red: 0.95, green: 0.98, blue: 0.97),
                Color(red: 0.9, green: 0.95, blue: 0.95)
            ],
            startPoint: .top,
            endPoint: .bottom
        )
        .ignoresSafeArea()
    }
    
    // MARK: - Header
    private var headerView: some View {
        HStack {
            // Profile Button
            Button(action: { showSettings = true }) {
                Circle()
                    .fill(Color.pink.opacity(0.3))
                    .frame(width: 44, height: 44)
                    .overlay(
                        Image(systemName: "person.fill")
                            .foregroundColor(.pink)
                    )
            }
            
            Spacer()
            
            // App Title
            VStack(spacing: 2) {
                Text("With")
                    .font(.custom("Georgia", size: 32))
                    .fontWeight(.medium)
                
                Text("不只看血糖数字，也关心你的感受")
                    .font(.system(size: 10, weight: .medium))
                    .foregroundColor(.gray)
                    .tracking(2)
            }
            
            Spacer()
            
            // Keep header centered after removing context switch entry point.
            Color.clear
                .frame(width: 44, height: 44)
        }
        .padding(.horizontal, 20)
        .padding(.top, 10)
    }
    
    // MARK: - Character Section
    private var characterSection: some View {
        VStack(spacing: 20) {
            // Side Navigation Icons removed: only Spacer, Character, Spacer
            
            HStack {
                Spacer()
                
                CharacterView(
                    character: appState.selectedCharacter,
                    isAnimating: $animateCharacter
                )
                .onTapGesture(count: 2) {
                    withAnimation {
                        showInputField = true
                    }
                }
                
                Spacer()
            }
            .padding(.horizontal, 20)
            
            // Support Message Bubble
            SupportMessageBubble(message: currentMessage)
                .onTapGesture {
                    withAnimation {
                        showInputField = true
                    }
                }
        }
    }
    
    // MARK: - Story Card
    private var storyCard: some View {
        Button(action: {}) {
            HStack(spacing: 12) {
                Image(systemName: "book.fill")
                    .foregroundColor(Color("AccentTeal"))
                    .font(.system(size: 20))
                
                VStack(alignment: .leading, spacing: 2) {
                    Text("你的故事")
                        .font(.system(size: 10, weight: .semibold))
                        .foregroundColor(.gray)
                        .tracking(1)
                    
                    Text("今日章节已准备好。")
                        .font(.system(size: 14))
                        .foregroundColor(.primary)
                }
                
                Spacer()
                
                Circle()
                    .fill(Color("AccentTeal"))
                    .frame(width: 8, height: 8)
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 14)
            .background(
                RoundedRectangle(cornerRadius: 16)
                    .fill(.white)
                    .shadow(color: .black.opacity(0.05), radius: 10, y: 5)
            )
        }
        .padding(.horizontal, 40)
    }
    
    // MARK: - Quick Input Suggestions
    private var quickInputSuggestions: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 10) {
                QuickInputChip(text: "今天状态还可以，记录一下") {
                    navigateToChatAndSend("今天状态还可以，记录一下")
                }
                QuickInputChip(text: "有点事情，想整理一下思绪") {
                    navigateToChatAndSend("有点事情，想整理一下思绪")
                }
                QuickInputChip(text: "今天的一个小开心") {
                    navigateToChatAndSend("今天的一个小开心")
                }
                QuickInputChip(text: "给自己一个小目标") {
                    navigateToChatAndSend("给自己一个小目标")
                }
            }
            .padding(.horizontal, 20)
        }
        .padding(.vertical, 10)
    }
    
    // MARK: - Helper Methods
    private func updateSupportMessage() {
        currentMessage = SupportMessage.getMessage(
            for: healthManager.glucoseLevel,
            context: appState.contextMode
        )
    }
    
    private func navigateToChatAndSend(_ text: String) {
        withAnimation { selectedTab = .chat }
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.15) {
            chatViewModel.sendMessage(text)
        }
    }
}

// MARK: - Side Icon Button
struct SideIconButton: View {
    let icon: String
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            Circle()
                .fill(Color.white.opacity(0.6))
                .frame(width: 44, height: 44)
                .overlay(
                    Image(systemName: icon)
                        .foregroundColor(.gray)
                )
        }
    }
}

// MARK: - Quick Input Chip
struct QuickInputChip: View {
    let text: String
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            Text(text)
                .font(.system(size: 13))
                .foregroundColor(.gray)
                .padding(.horizontal, 16)
                .padding(.vertical, 10)
                .background(
                    RoundedRectangle(cornerRadius: 20)
                        .fill(Color.white)
                        .shadow(color: .black.opacity(0.05), radius: 5)
                )
        }
    }
}

#Preview {
    HomeView(selectedTab: .constant(.home), showSettings: .constant(false))
        .environmentObject(AppState())
        .environmentObject(HealthManager())
        .environmentObject(ChatViewModel())
}
