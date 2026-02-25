//
//  CGMConnectionView.swift
//  WithApp
//
//  View for connecting to CGM devices via Bluetooth
//

import SwiftUI

struct CGMConnectionView: View {
    @EnvironmentObject var appState: AppState
    @EnvironmentObject var healthManager: HealthManager
    @Environment(\.dismiss) var dismiss
    
    @State private var isSearching = false
    @State private var foundDevices: [CGMDevice] = []
    @State private var connectingDevice: CGMDevice?
    @State private var connectionProgress: Double = 0
    
    var body: some View {
        NavigationStack {
            VStack(spacing: 24) {
                // Status Icon
                connectionStatusIcon
                
                // Status Text
                statusText
                
                // Device List or Instructions
                if appState.cgmConnected {
                    connectedDeviceInfo
                } else if isSearching {
                    searchingView
                } else if !foundDevices.isEmpty {
                    deviceList
                } else {
                    instructionsView
                }
                
                Spacer()
                
                // Action Button
                actionButton
            }
            .padding(.horizontal, 20)
            .padding(.vertical, 24)
            .background(Color(red: 0.97, green: 0.98, blue: 0.99))
            .navigationTitle("CGM 设备")
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
    
    private var connectionStatusIcon: some View {
        ZStack {
            Circle()
                .fill(appState.cgmConnected ? Color.green.opacity(0.2) : Color.blue.opacity(0.2))
                .frame(width: 120, height: 120)
            
            if let _ = connectingDevice {
                Circle()
                    .trim(from: 0, to: connectionProgress)
                    .stroke(Color("AccentTeal"), lineWidth: 4)
                    .frame(width: 110, height: 110)
                    .rotationEffect(.degrees(-90))
            }
            
            Image(systemName: appState.cgmConnected ? "checkmark.circle.fill" : "waveform.path.ecg")
                .font(.system(size: 50))
                .foregroundColor(appState.cgmConnected ? .green : Color("AccentTeal"))
        }
    }
    
    private var statusText: some View {
        VStack(spacing: 8) {
            Text(appState.cgmConnected ? "已连接" : "未连接")
                .font(.system(size: 24, weight: .bold))
            
            if appState.cgmConnected {
                Text("你的 CGM 正在同步数据")
                    .font(.system(size: 16))
                    .foregroundColor(.gray)
            } else if isSearching {
                Text("正在搜索附近的设备...")
                    .font(.system(size: 16))
                    .foregroundColor(.gray)
            } else {
                Text("连接你的 CGM 以启用实时洞见")
                    .font(.system(size: 16))
                    .foregroundColor(.gray)
                    .multilineTextAlignment(.center)
            }
        }
    }
    
    private var connectedDeviceInfo: some View {
        VStack(spacing: 16) {
            HStack(spacing: 16) {
                Image(systemName: "waveform.path.ecg")
                    .font(.system(size: 24))
                    .foregroundColor(Color("AccentTeal"))
                    .frame(width: 50, height: 50)
                    .background(Color("AccentTeal").opacity(0.1))
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                
                VStack(alignment: .leading, spacing: 4) {
                    Text("Dexcom G7")
                        .font(.system(size: 16, weight: .semibold))
                    
                    HStack(spacing: 4) {
                        Circle()
                            .fill(Color.green)
                            .frame(width: 8, height: 8)
                        Text("通过蓝牙已连接")
                            .font(.system(size: 14))
                            .foregroundColor(.gray)
                    }
                }
                
                Spacer()
            }
            .padding(16)
            .background(
                RoundedRectangle(cornerRadius: 16)
                    .fill(Color.white)
                    .shadow(color: .black.opacity(0.05), radius: 10)
            )
            
            HStack {
                Image(systemName: "arrow.triangle.2.circlepath")
                    .foregroundColor(.gray)
                Text("上次同步：刚刚")
                    .font(.system(size: 14))
                    .foregroundColor(.gray)
            }
        }
    }
    
    private var searchingView: some View {
        VStack(spacing: 16) {
            ProgressView()
                .scaleEffect(1.5)
            
            Text("正在查找设备...")
                .font(.system(size: 14))
                .foregroundColor(.gray)
        }
        .frame(height: 200)
    }
    
    private var deviceList: some View {
        VStack(spacing: 12) {
            ForEach(foundDevices) { device in
                Button(action: { connectToDevice(device) }) {
                    HStack(spacing: 16) {
                        Image(systemName: "waveform.path.ecg")
                            .font(.system(size: 24))
                            .foregroundColor(Color("AccentTeal"))
                            .frame(width: 50, height: 50)
                            .background(Color("AccentTeal").opacity(0.1))
                            .clipShape(RoundedRectangle(cornerRadius: 12))
                        
                        VStack(alignment: .leading, spacing: 4) {
                            Text(device.name)
                                .font(.system(size: 16, weight: .semibold))
                                .foregroundColor(.primary)
                            
                            Text(device.type)
                                .font(.system(size: 14))
                                .foregroundColor(.gray)
                        }
                        
                        Spacer()
                        
                        if connectingDevice?.id == device.id {
                            ProgressView()
                        } else {
                            Image(systemName: "chevron.right")
                                .foregroundColor(.gray)
                        }
                    }
                    .padding(16)
                    .background(
                        RoundedRectangle(cornerRadius: 16)
                            .fill(Color.white)
                            .shadow(color: .black.opacity(0.05), radius: 10)
                    )
                }
                .disabled(connectingDevice != nil)
            }
        }
    }
    
    private var instructionsView: some View {
        VStack(alignment: .leading, spacing: 16) {
            InstructionRow(number: "1", text: "确保你的 CGM 在身边")
            InstructionRow(number: "2", text: "在手机上开启蓝牙")
            InstructionRow(number: "3", text: "点击下方“搜索设备”")
        }
        .padding(20)
        .background(
            RoundedRectangle(cornerRadius: 16)
                .fill(Color.white)
                .shadow(color: .black.opacity(0.05), radius: 10)
        )
    }
    
    private var actionButton: some View {
        Button(action: {
            if appState.cgmConnected {
                disconnectDevice()
            } else {
                searchForDevices()
            }
        }) {
            Text(appState.cgmConnected ? "断开连接" : (isSearching ? "正在搜索..." : "搜索设备"))
                .font(.system(size: 16, weight: .semibold))
                .foregroundColor(.white)
                .frame(maxWidth: .infinity)
                .padding(.vertical, 16)
                .background(
                    Capsule()
                        .fill(appState.cgmConnected ? Color.red : Color("AccentTeal"))
                )
        }
        .disabled(isSearching)
        .padding(.horizontal, 20)
    }
    
    private func searchForDevices() {
        isSearching = true
        foundDevices = []
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            foundDevices = [
                CGMDevice(id: "1", name: "Dexcom G7", type: "CGM 传感器"),
                CGMDevice(id: "2", name: "FreeStyle Libre 3", type: "CGM 传感器")
            ]
            isSearching = false
        }
    }
    
    private func connectToDevice(_ device: CGMDevice) {
        connectingDevice = device
        connectionProgress = 0
        
        Timer.scheduledTimer(withTimeInterval: 0.1, repeats: true) { timer in
            connectionProgress += 0.05
            
            if connectionProgress >= 1.0 {
                timer.invalidate()
                appState.cgmConnected = true
                connectingDevice = nil
                foundDevices = []
            }
        }
    }
    
    private func disconnectDevice() {
        appState.cgmConnected = false
    }
}

struct InstructionRow: View {
    let number: String
    let text: String
    
    var body: some View {
        HStack(spacing: 12) {
            Text(number)
                .font(.system(size: 14, weight: .bold))
                .foregroundColor(.white)
                .frame(width: 24, height: 24)
                .background(Circle().fill(Color("AccentTeal")))
            
            Text(text)
                .font(.system(size: 15))
                .foregroundColor(.primary)
        }
    }
}

struct CGMDevice: Identifiable {
    let id: String
    let name: String
    let type: String
}

struct CharacterSelectionView: View {
    @EnvironmentObject var appState: AppState
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        NavigationStack {
            VStack(spacing: 24) {
                Text("选择你的伙伴")
                    .font(.system(size: 24, weight: .bold))
                
                Text("选择一个与你共鸣的角色")
                    .font(.system(size: 16))
                    .foregroundColor(.gray)
                
                VStack(spacing: 12) {
                    CharacterView(character: appState.selectedCharacter, isAnimating: .constant(true))
                        .frame(height: 120)
                    
                    Text(appState.selectedCharacter.displayName)
                        .font(.system(size: 20, weight: .semibold))
                }
                .padding(.vertical, 20)
                
                LazyVGrid(columns: [
                    GridItem(.flexible()),
                    GridItem(.flexible()),
                    GridItem(.flexible())
                ], spacing: 16) {
                    ForEach(CharacterType.allCases) { character in
                        Button(action: {
                            withAnimation {
                                appState.selectedCharacter = character
                            }
                        }) {
                            VStack(spacing: 8) {
                                Text(character.emoji)
                                    .font(.system(size: 36))
                                
                                Text(character.displayName)
                                    .font(.system(size: 12))
                                    .foregroundColor(.primary)
                            }
                            .frame(maxWidth: .infinity)
                            .padding(.vertical, 16)
                            .background(
                                RoundedRectangle(cornerRadius: 16)
                                    .fill(Color.white)
                                    .shadow(color: .black.opacity(0.05), radius: 5)
                            )
                            .overlay(
                                RoundedRectangle(cornerRadius: 16)
                                    .stroke(
                                        appState.selectedCharacter == character ? Color("AccentTeal") : Color.clear,
                                        lineWidth: 2
                                    )
                            )
                        }
                    }
                }
                .padding(.horizontal, 20)
                
                Spacer()
            }
            .padding(.vertical, 24)
            .background(Color(red: 0.97, green: 0.98, blue: 0.99))
            .navigationTitle("角色")
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
}

#Preview("CGM Connection") {
    CGMConnectionView()
        .environmentObject(AppState())
        .environmentObject(HealthManager())
}

#Preview("Character Selection") {
    CharacterSelectionView()
        .environmentObject(AppState())
}

