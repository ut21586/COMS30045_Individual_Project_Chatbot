//
//  ProfileView.swift
//  WithApp
//
//  User profile view with CGM connection status
//

import SwiftUI

struct ProfileView: View {
    @EnvironmentObject var appState: AppState
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 24) {
                    // Profile Header
                    profileHeader
                    
                    // Connection Status
                    connectionStatus
                    
                    // Quick Settings
                    quickSettings
                    
                    // Statistics
                    statisticsSection
                }
                .padding(.horizontal, 20)
                .padding(.vertical, 16)
            }
            .background(Color(red: 0.97, green: 0.98, blue: 0.99))
            .navigationTitle("")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button("完成") {
                        dismiss()
                    }
                    .foregroundColor(Color("AccentTeal"))
                }
            }
        }
    }
    
    private var profileHeader: some View {
        VStack(spacing: 16) {
            Circle()
                .fill(Color.pink.opacity(0.3))
                .frame(width: 100, height: 100)
                .overlay(
                    Image(systemName: "person.fill")
                        .font(.system(size: 40))
                        .foregroundColor(.pink)
                )
            
            VStack(spacing: 4) {
                Text(appState.userName)
                    .font(.system(size: 24, weight: .bold))
                
                Text("自 2026 年 1 月起成为会员")
                    .font(.system(size: 14))
                    .foregroundColor(.gray)
            }
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 24)
    }
    
    private var connectionStatus: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("已连接的设备")
                .font(.system(size: 14, weight: .semibold))
                .foregroundColor(.gray)
            
            HStack(spacing: 16) {
                DeviceStatusCard(
                    icon: "waveform.path.ecg",
                    name: "CGM",
                    status: appState.cgmConnected ? "Connected" : "Not Connected",
                    isConnected: appState.cgmConnected
                )
                
                DeviceStatusCard(
                    icon: "applewatch",
                    name: "手表",
                    status: "已连接",
                    isConnected: true
                )
            }
        }
    }
    
    private var quickSettings: some View {
        VStack(spacing: 0) {
            ProfileMenuItem(
                icon: "person.crop.square",
                title: "偏好设置",
                action: {}
            )
            
            Divider().padding(.leading, 56)
            
            ProfileMenuItem(
                icon: "bell.fill",
                title: "通知",
                action: {}
            )
            
            Divider().padding(.leading, 56)
            
            ProfileMenuItem(
                icon: "rectangle.portrait.and.arrow.right",
                title: "退出登录",
                isDestructive: true,
                action: {}
            )
        }
        .background(
            RoundedRectangle(cornerRadius: 16)
                .fill(Color.white)
                .shadow(color: .black.opacity(0.05), radius: 10)
        )
    }
    
    private var statisticsSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("本周")
                .font(.system(size: 14, weight: .semibold))
                .foregroundColor(.gray)
            
            HStack(spacing: 12) {
                StatCard(title: "对话", value: "12", icon: "message.fill")
                StatCard(title: "签到", value: "28", icon: "checkmark.circle.fill")
            }
            
            HStack(spacing: 12) {
                StatCard(title: "查看报告", value: "5", icon: "doc.text.fill")
                StatCard(title: "平均心情", value: "😊", icon: "face.smiling.fill")
            }
        }
    }
}

struct DeviceStatusCard: View {
    let icon: String
    let name: String
    let status: String
    let isConnected: Bool
    
    var body: some View {
        VStack(spacing: 8) {
            Image(systemName: icon)
                .font(.system(size: 28))
                .foregroundColor(isConnected ? Color("AccentTeal") : .gray)
            
            Text(name)
                .font(.system(size: 14, weight: .medium))
            
            HStack(spacing: 4) {
                Circle()
                    .fill(isConnected ? Color.green : Color.gray)
                    .frame(width: 6, height: 6)
                
                Text(status)
                    .font(.system(size: 12))
                    .foregroundColor(.gray)
            }
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 20)
        .background(
            RoundedRectangle(cornerRadius: 16)
                .fill(Color.white)
                .shadow(color: .black.opacity(0.05), radius: 10)
        )
    }
}

struct ProfileMenuItem: View {
    let icon: String
    let title: String
    var isDestructive: Bool = false
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 12) {
                Image(systemName: icon)
                    .font(.system(size: 20))
                    .foregroundColor(isDestructive ? .red : Color("AccentTeal"))
                    .frame(width: 36, height: 36)
                    .background((isDestructive ? Color.red : Color("AccentTeal")).opacity(0.1))
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                
                Text(title)
                    .font(.system(size: 16))
                    .foregroundColor(isDestructive ? .red : .primary)
                
                Spacer()
                
                Image(systemName: "chevron.right")
                    .font(.system(size: 14))
                    .foregroundColor(.gray)
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 12)
        }
    }
}

struct StatCard: View {
    let title: String
    let value: String
    let icon: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            HStack {
                Image(systemName: icon)
                    .foregroundColor(Color("AccentTeal"))
                Spacer()
            }
            
            Text(value)
                .font(.system(size: 24, weight: .bold))
            
            Text(title)
                .font(.system(size: 12))
                .foregroundColor(.gray)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(16)
        .background(
            RoundedRectangle(cornerRadius: 16)
                .fill(Color.white)
                .shadow(color: .black.opacity(0.05), radius: 10)
        )
    }
}

#Preview {
    ProfileView()
        .environmentObject(AppState())
}

