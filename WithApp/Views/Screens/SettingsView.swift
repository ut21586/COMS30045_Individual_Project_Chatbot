//
//  SettingsView.swift
//  WithApp
//
//  Settings view for managing preferences, device connections, and features
//

import SwiftUI

struct SettingsView: View {
    @EnvironmentObject var appState: AppState
    @EnvironmentObject var healthManager: HealthManager
    @Environment(\.dismiss) var dismiss
    
    @State private var showCGMConnection = false
    @State private var showCharacterSelection = false
    
    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                // Header
                headerView
                
                // Profile Section
                profileSection
                
                // Device Connections
                deviceSection
                
                // Preferences
                preferencesSection
                
                // Notifications
                notificationsSection
                
                // Features Toggle
                featuresSection
                
                // Privacy
                privacySection
                
                // About
                aboutSection
            }
            .padding(.horizontal, 20)
            .padding(.vertical, 16)
        }
        .background(Color(red: 0.97, green: 0.98, blue: 0.99))
        .sheet(isPresented: $showCGMConnection) {
            CGMConnectionView()
        }
        .sheet(isPresented: $showCharacterSelection) {
            CharacterSelectionView()
        }
    }
    
    // MARK: - Header
    private var headerView: some View {
        HStack {
            Button(action: { dismiss() }) {
                Image(systemName: "chevron.left")
                    .foregroundColor(.gray)
                    .font(.system(size: 22, weight: .medium))
            }
            
            Text("设置")
                .font(.system(size: 28, weight: .bold))
            
            Spacer()
        }
    }
    
    // MARK: - Profile Section
    private var profileSection: some View {
        VStack(spacing: 0) {
            HStack(spacing: 16) {
                Circle()
                    .fill(Color.pink.opacity(0.3))
                    .frame(width: 60, height: 60)
                    .overlay(
                        Image(systemName: "person.fill")
                            .font(.system(size: 24))
                            .foregroundColor(.pink)
                    )
                
                VStack(alignment: .leading, spacing: 4) {
                    Text(appState.userName)
                        .font(.system(size: 18, weight: .semibold))
                    
                    HStack(spacing: 4) {
                        Circle()
                            .fill(appState.cgmConnected ? Color.green : Color.gray)
                            .frame(width: 8, height: 8)
                        Text(appState.cgmConnected ? "已连接" : "未连接")
                            .font(.system(size: 14))
                            .foregroundColor(.gray)
                    }
                }
                
                Spacer()
                
                Image(systemName: "chevron.right")
                    .foregroundColor(.gray)
            }
            .padding(20)
        }
        .background(
            RoundedRectangle(cornerRadius: 16)
                .fill(Color.white)
                .shadow(color: .black.opacity(0.05), radius: 10)
        )
    }
    
    // MARK: - Device Section
    private var deviceSection: some View {
        SettingsSection(title: "设备") {
            SettingsRow(
                icon: "waveform.path.ecg",
                iconColor: .blue,
                title: "CGM 设备",
                subtitle: appState.cgmConnected ? "Dexcom G7" : "未连接",
                showChevron: true
            ) {
                showCGMConnection = true
            }
            
            Divider().padding(.leading, 56)
            
            SettingsRow(
                icon: "applewatch",
                iconColor: .orange,
                title: "Apple Watch",
                subtitle: "已连接",
                showChevron: true
            ) {}
            
            Divider().padding(.leading, 56)
            
            SettingsRow(
                icon: "iphone",
                iconColor: .gray,
                title: "iPhone 健康",
                subtitle: "同步已启用",
                showChevron: true
            ) {}
        }
    }
    
    // MARK: - Preferences Section
    private var preferencesSection: some View {
        SettingsSection(title: "偏好设置") {
            SettingsRow(
                icon: "person.crop.square",
                iconColor: Color("AccentTeal"),
                title: "角色",
                subtitle: appState.selectedCharacter.displayName,
                showChevron: true
            ) {
                showCharacterSelection = true
            }
            
            Divider().padding(.leading, 56)
            
            HStack(spacing: 12) {
                Image(systemName: "globe")
                    .font(.system(size: 20))
                    .foregroundColor(.purple)
                    .frame(width: 36, height: 36)
                    .background(Color.purple.opacity(0.1))
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                
                Text("语言")
                    .font(.system(size: 16))
                    .foregroundColor(.primary)
                
                Spacer()
                
                Picker("语言", selection: $appState.appLanguage) {
                    ForEach(AppLanguage.allCases, id: \.self) { lang in
                        Text(lang.displayName).tag(lang)
                    }
                }
                .pickerStyle(.menu)
                .padding(.leading, 12)
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 12)
            
            Divider().padding(.leading, 56)
            
            SettingsRow(
                icon: "textformat.size",
                iconColor: .indigo,
                title: "文字大小",
                subtitle: "中等",
                showChevron: true
            ) {}
        }
    }
    
    // MARK: - Notifications Section
    private var notificationsSection: some View {
        SettingsSection(title: "通知") {
            SettingsToggleRow(
                icon: "bell.fill",
                iconColor: .red,
                title: "推送通知",
                isOn: $appState.notificationsEnabled
            )
            
            Divider().padding(.leading, 56)
            
            SettingsRow(
                icon: "clock.fill",
                iconColor: .orange,
                title: "勿扰时段",
                subtitle: "22:00 - 7:00",
                showChevron: true
            ) {}
        }
    }
    
    // MARK: - Features Section
    private var featuresSection: some View {
        SettingsSection(title: "功能") {
            SettingsToggleRow(
                icon: "face.smiling",
                iconColor: .yellow,
                title: "情感支持",
                isOn: .constant(true)
            )

            Divider().padding(.leading, 56)
            
            SettingsToggleRow(
                icon: "figure.walk",
                iconColor: .green,
                title: "活动建议",
                isOn: .constant(true)
            )
            
            Divider().padding(.leading, 56)
            
            SettingsToggleRow(
                icon: "fork.knife",
                iconColor: .orange,
                title: "饮食推荐",
                isOn: .constant(true)
            )
            
            Divider().padding(.leading, 56)
            
            SettingsToggleRow(
                icon: "location.fill",
                iconColor: .purple,
                title: "情境识别",
                isOn: .constant(true)
            )
        }
    }
    
    // MARK: - Privacy Section
    private var privacySection: some View {
        SettingsSection(title: "隐私与数据") {
            SettingsRow(
                icon: "hand.raised.fill",
                iconColor: .blue,
                title: "数据权限",
                subtitle: "",
                showChevron: true
            ) {}
            
            Divider().padding(.leading, 56)
            
            SettingsRow(
                icon: "trash.fill",
                iconColor: .red,
                title: "删除所有数据",
                subtitle: "",
                showChevron: true
            ) {}
            
            Divider().padding(.leading, 56)
            
            SettingsRow(
                icon: "doc.text.fill",
                iconColor: .gray,
                title: "隐私政策",
                subtitle: "",
                showChevron: true
            ) {}
        }
    }
    
    // MARK: - About Section
    private var aboutSection: some View {
        SettingsSection(title: "关于") {
            SettingsRow(
                icon: "info.circle.fill",
                iconColor: .gray,
                title: "版本",
                subtitle: "1.0.0",
                showChevron: false
            ) {}
            
            Divider().padding(.leading, 56)
            
            SettingsRow(
                icon: "questionmark.circle.fill",
                iconColor: Color("AccentTeal"),
                title: "帮助与支持",
                subtitle: "",
                showChevron: true
            ) {}
            
            Divider().padding(.leading, 56)
            
            Button(action: {}) {
                HStack {
                    Image(systemName: "rectangle.portrait.and.arrow.right")
                        .font(.system(size: 20))
                        .foregroundColor(.red)
                        .frame(width: 36, height: 36)
                        .background(Color.red.opacity(0.1))
                        .clipShape(RoundedRectangle(cornerRadius: 8))
                    
                    Text("退出登录")
                        .font(.system(size: 16))
                        .foregroundColor(.red)
                    
                    Spacer()
                }
                .padding(.horizontal, 16)
                .padding(.vertical, 12)
            }
        }
    }
}

// MARK: - Settings Section
struct SettingsSection<Content: View>: View {
    let title: String
    @ViewBuilder let content: Content
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text(title)
                .font(.system(size: 14, weight: .semibold))
                .foregroundColor(.gray)
                .padding(.leading, 4)
            
            VStack(spacing: 0) {
                content
            }
            .background(
                RoundedRectangle(cornerRadius: 16)
                    .fill(Color.white)
                    .shadow(color: .black.opacity(0.05), radius: 10)
            )
        }
    }
}

// MARK: - Settings Row
struct SettingsRow: View {
    let icon: String
    let iconColor: Color
    let title: String
    let subtitle: String
    let showChevron: Bool
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 12) {
                Image(systemName: icon)
                    .font(.system(size: 20))
                    .foregroundColor(iconColor)
                    .frame(width: 36, height: 36)
                    .background(iconColor.opacity(0.1))
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                
                Text(title)
                    .font(.system(size: 16))
                    .foregroundColor(.primary)
                
                Spacer()
                
                if !subtitle.isEmpty {
                    Text(subtitle)
                        .font(.system(size: 14))
                        .foregroundColor(.gray)
                }
                
                if showChevron {
                    Image(systemName: "chevron.right")
                        .font(.system(size: 14))
                        .foregroundColor(.gray)
                }
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 12)
        }
    }
}

// MARK: - Settings Toggle Row
struct SettingsToggleRow: View {
    let icon: String
    let iconColor: Color
    let title: String
    @Binding var isOn: Bool
    
    var body: some View {
        HStack(spacing: 12) {
            Image(systemName: icon)
                .font(.system(size: 20))
                .foregroundColor(iconColor)
                .frame(width: 36, height: 36)
                .background(iconColor.opacity(0.1))
                .clipShape(RoundedRectangle(cornerRadius: 8))
            
            Text(title)
                .font(.system(size: 16))
                .foregroundColor(.primary)
            
            Spacer()
            
            Toggle("", isOn: $isOn)
                .tint(Color("AccentTeal"))
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 12)
    }
}

#Preview {
    SettingsView()
        .environmentObject(AppState())
        .environmentObject(HealthManager())
}
