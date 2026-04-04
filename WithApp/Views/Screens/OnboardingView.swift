//
//  OnboardingView.swift
//  WithApp
//
//  Onboarding flow for new users
//

import SwiftUI

struct OnboardingView: View {
    @EnvironmentObject var appState: AppState
    @EnvironmentObject var healthManager: HealthManager
    
    @State private var currentPage = 0
    @State private var userName = ""
    @State private var selectedCharacter: CharacterType = .robot
    @State private var height: String = ""
    @State private var weight: String = ""
    @State private var age: String = ""
    @State private var genderIndex: Int = 0
    @State private var diagnosisDate: Date = Date()
    @State private var usesMedication: Bool = false
    @State private var usesInsulin: Bool = false
    @State private var latestHbA1c: String = ""
    @State private var moodIndex: Int = 2
    @State private var hasStress: Bool = false
    
    var body: some View {
        ZStack {
            // Background
            LinearGradient(
                colors: [
                    Color(red: 0.95, green: 0.98, blue: 0.97),
                    Color(red: 0.9, green: 0.95, blue: 0.95)
                ],
                startPoint: .top,
                endPoint: .bottom
            )
            .ignoresSafeArea()
            
            VStack {
                // Page Content
                TabView(selection: $currentPage) {
                    WelcomePage()
                        .tag(0)
                    
                    NameInputPage(userName: $userName)
                        .tag(1)
                    
                    CharacterPage(selectedCharacter: $selectedCharacter)
                        .tag(2)
                    
                    OptionalInfoPage(
                        height: $height,
                        weight: $weight,
                        age: $age,
                        genderIndex: $genderIndex,
                        diagnosisDate: $diagnosisDate,
                        usesMedication: $usesMedication,
                        usesInsulin: $usesInsulin,
                        latestHbA1c: $latestHbA1c,
                        moodIndex: $moodIndex,
                        hasStress: $hasStress
                    )
                    .tag(3)
                    
                    PermissionsPage()
                        .tag(4)
                    
                    ConnectDevicePage()
                        .tag(5)
                    
                    CompletionPage()
                        .tag(6)
                }
                .tabViewStyle(.page(indexDisplayMode: .never))
                
                // Progress Dots
                HStack(spacing: 8) {
                    ForEach(0..<7) { index in
                        Circle()
                            .fill(currentPage == index ? Color("AccentTeal") : Color.gray.opacity(0.3))
                            .frame(width: 8, height: 8)
                    }
                }
                .padding(.bottom, 20)
                
                // Navigation Buttons
                HStack(spacing: 16) {
                    if currentPage > 0 {
                        Button(action: previousPage) {
                            Text("返回")
                                .font(.system(size: 16, weight: .medium))
                                .foregroundColor(.gray)
                                .padding(.horizontal, 32)
                                .padding(.vertical, 14)
                                .background(
                                    Capsule()
                                        .stroke(Color.gray.opacity(0.3), lineWidth: 1)
                                )
                        }
                    }
                    
                    Button(action: nextPage) {
                        Text(currentPage == 6 ? "开始使用" : "继续")
                            .font(.system(size: 16, weight: .semibold))
                            .foregroundColor(.white)
                            .padding(.horizontal, 32)
                            .padding(.vertical, 14)
                            .background(
                                Capsule()
                                    .fill(Color("AccentTeal"))
                            )
                    }
                }
                .padding(.horizontal, 40)
                .padding(.bottom, 40)
            }
        }
    }
    
    private func nextPage() {
        withAnimation {
            if currentPage == 6 {
                completeOnboarding()
            } else {
                currentPage += 1
            }
        }
    }
    
    private func previousPage() {
        withAnimation {
            currentPage -= 1
        }
    }
    
    private func completeOnboarding() {
        appState.userName = userName.isEmpty ? "User" : userName
        appState.selectedCharacter = selectedCharacter

        // Persist to UserDefaults (as a fallback database)
        let defaults = UserDefaults.standard
        defaults.set(height, forKey: "onboard.height")
        defaults.set(weight, forKey: "onboard.weight")
        defaults.set(age, forKey: "onboard.age")
        defaults.set(genderIndex, forKey: "onboard.genderIndex")
        defaults.set(diagnosisDate.timeIntervalSince1970, forKey: "onboard.diagnosisDate")
        defaults.set(usesMedication, forKey: "onboard.usesMedication")
        defaults.set(usesInsulin, forKey: "onboard.usesInsulin")
        defaults.set(latestHbA1c, forKey: "onboard.latestHbA1c")
        defaults.set(moodIndex, forKey: "onboard.moodIndex")
        defaults.set(hasStress, forKey: "onboard.hasStress")

        // Best-effort assign into AppState if it defines matching properties
        // (Safe optional assignment using key paths would need definitions; here we call helper if available)
        if let assign = (appState as AnyObject) as? OnboardingDataReceivable {
            assign.applyOnboardingData(
                height: height,
                weight: weight,
                age: age,
                genderIndex: genderIndex,
                diagnosisDate: diagnosisDate,
                usesMedication: usesMedication,
                usesInsulin: usesInsulin,
                latestHbA1c: latestHbA1c,
                moodIndex: moodIndex,
                hasStress: hasStress
            )
        }

        appState.isOnboarded = true
    }
}

// MARK: - Welcome Page
struct WelcomePage: View {
    var body: some View {
        VStack(spacing: 32) {
            Spacer()
            
            // Logo
            VStack(spacing: 8) {
                Text("With")
                    .font(.custom("Georgia", size: 56))
                    .fontWeight(.medium)
                
                Text("始终与你同行")
                    .font(.system(size: 12, weight: .medium))
                    .foregroundColor(.gray)
                    .tracking(3)
            }
            
            // Character preview
            CharacterView(character: .robot, isAnimating: .constant(true))
                .frame(height: 150)
            
            Text("你的视频头像是系统默认图标")
                .font(.system(size: 14))
                .foregroundColor(.gray)
                .multilineTextAlignment(.center)
            
            // Description
            VStack(spacing: 12) {
                Text("你的每日健康陪伴")
                    .font(.system(size: 24, weight: .semibold))
                    .multilineTextAlignment(.center)
                
                Text("为你量身定制的情感支持、洞见与指导。")
                    .font(.system(size: 16))
                    .foregroundColor(.gray)
                    .multilineTextAlignment(.center)
            }
            
            Spacer()
            Spacer()
        }
        .padding(.horizontal, 40)
    }
}

// MARK: - Name Input Page
struct NameInputPage: View {
    @Binding var userName: String
    
    var body: some View {
        VStack(spacing: 32) {
            Spacer()
            
            Text("我该怎么称呼你？")
                .font(.system(size: 28, weight: .bold))
            
            Text("告诉我你的名字，我会为你提供更个性化的体验。")
                .font(.system(size: 16))
                .foregroundColor(.gray)
                .multilineTextAlignment(.center)
            
            TextField("你的名字", text: $userName)
                .font(.system(size: 20))
                .multilineTextAlignment(.center)
                .padding(.vertical, 16)
                .background(
                    RoundedRectangle(cornerRadius: 12)
                        .fill(Color.white)
                        .shadow(color: .black.opacity(0.05), radius: 10)
                )
                .padding(.horizontal, 40)
            
            Spacer()
            Spacer()
        }
        .padding(.horizontal, 40)
    }
}

// MARK: - Character Page
struct CharacterPage: View {
    @Binding var selectedCharacter: CharacterType
    
    var body: some View {
        VStack(spacing: 32) {
            Spacer()
            
            Text("选择你的伙伴")
                .font(.system(size: 28, weight: .bold))
            
            Text("选择一个你想与之互动的角色。")
                .font(.system(size: 16))
                .foregroundColor(.gray)
                .multilineTextAlignment(.center)
            
            // Character Grid
            LazyVGrid(columns: [
                GridItem(.flexible()),
                GridItem(.flexible()),
                GridItem(.flexible())
            ], spacing: 20) {
                ForEach(CharacterType.allCases) { character in
                    Button(action: {
                        withAnimation { selectedCharacter = character }
                    }) {
                        VStack(spacing: 12) {
                            Text(character.emoji)
                                .font(.system(size: 40))
                            Text(character.displayName)
                                .font(.system(size: 14, weight: .medium))
                                .foregroundColor(.primary)
                        }
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 20)
                        .background(
                            RoundedRectangle(cornerRadius: 16)
                                .fill(Color.white)
                                .shadow(color: .black.opacity(0.05), radius: 10)
                        )
                        .overlay(
                            RoundedRectangle(cornerRadius: 16)
                                .stroke(selectedCharacter == character ? Color("AccentTeal") : Color.clear, lineWidth: 2)
                        )
                    }
                }
            }
            .padding(.horizontal, 20)
            
            Spacer()
            Spacer()
        }
        .padding(.horizontal, 20)
    }
}

// MARK: - Optional Info Page
struct OptionalInfoPage: View {
    @Binding var height: String
    @Binding var weight: String
    @Binding var age: String
    @Binding var genderIndex: Int
    @Binding var diagnosisDate: Date
    @Binding var usesMedication: Bool
    @Binding var usesInsulin: Bool
    @Binding var latestHbA1c: String
    @Binding var moodIndex: Int
    @Binding var hasStress: Bool

    private let genders = ["未指定", "男", "女", "其他"]
    private let moods = ["😢", "🙁", "😐", "🙂", "😊"]

    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                Spacer().frame(height: 10)

                Text("可选信息（可跳过）")
                    .font(.system(size: 28, weight: .bold))

                Text("这些信息完全自愿填写，有助于为你提供更贴合的支持。你可以随时在设置或资料中补充或修改。")
                    .font(.system(size: 14))
                    .foregroundColor(.gray)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 20)

                // 基本人⼝学信息
                SettingsSection(title: "基本人口学信息（可选）") {
                    VStack(spacing: 12) {
                        LabeledField(label: "身高(cm)", placeholder: "例如 170", text: $height, keyboard: .numberPad)
                        Divider().padding(.leading, 0)
                        LabeledField(label: "体重(kg)", placeholder: "例如 65", text: $weight, keyboard: .numberPad)
                        Divider().padding(.leading, 0)
                        LabeledField(label: "年龄", placeholder: "例如 30", text: $age, keyboard: .numberPad)
                        Divider().padding(.leading, 0)
                        PickerRow(label: "性别") {
                            Picker("性别", selection: $genderIndex) {
                                ForEach(0..<genders.count, id: \.self) { idx in
                                    Text(genders[idx]).tag(idx)
                                }
                            }.pickerStyle(.menu)
                        }
                    }
                    .padding(.horizontal, 16)
                    .padding(.vertical, 12)
                }

                // 糖尿病基础信息
                SettingsSection(title: "糖尿病基础信息（可选）") {
                    VStack(spacing: 12) {
                        DatePickerRow(label: "诊断时间", date: $diagnosisDate)
                        Divider().padding(.leading, 0)
                        ToggleRow(label: "是否使用药物", isOn: $usesMedication)
                        Divider().padding(.leading, 0)
                        ToggleRow(label: "是否使用胰岛素", isOn: $usesInsulin)
                        Divider().padding(.leading, 0)
                        LabeledField(label: "最近一次 HbA1c(%)", placeholder: "例如 6.8", text: $latestHbA1c, keyboard: .decimalPad)
                    }
                    .padding(.horizontal, 16)
                    .padding(.vertical, 12)
                }

                // 情绪支持相关信息
                SettingsSection(title: "情绪支持（可选）") {
                    VStack(spacing: 12) {
                        PickerRow(label: "现在的心情") {
                            Picker("现在的心情", selection: $moodIndex) {
                                ForEach(0..<moods.count, id: \.self) { idx in
                                    Text(moods[idx]).tag(idx)
                                }
                            }.pickerStyle(.segmented)
                        }
                        Divider().padding(.leading, 0)
                        ToggleRow(label: "是否有压力", isOn: $hasStress)
                    }
                    .padding(.horizontal, 16)
                    .padding(.vertical, 12)
                }

                Text("跳过此步 → 在右下角继续")
                    .font(.system(size: 12))
                    .foregroundColor(.gray)
                    .padding(.top, 4)

                Spacer(minLength: 10)
            }
            .padding(.horizontal, 20)
        }
    }
}

// MARK: - Small reusable rows used in OptionalInfoPage
struct LabeledField: View {
    let label: String
    let placeholder: String
    @Binding var text: String
    var keyboard: UIKeyboardType = .default

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(label)
                .font(.system(size: 14))
                .foregroundColor(.gray)
            TextField(placeholder, text: $text)
                .keyboardType(keyboard)
                .textFieldStyle(.plain)
                .padding(.horizontal, 12)
                .padding(.vertical, 10)
                .background(
                    RoundedRectangle(cornerRadius: 10)
                        .fill(Color.white)
                        .shadow(color: .black.opacity(0.05), radius: 6)
                )
        }
    }
}

struct PickerRow<Content: View>: View {
    let label: String
    @ViewBuilder var content: Content

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(label)
                .font(.system(size: 14))
                .foregroundColor(.gray)
            HStack { content }
                .padding(.horizontal, 12)
                .padding(.vertical, 10)
                .background(
                    RoundedRectangle(cornerRadius: 10)
                        .fill(Color.white)
                        .shadow(color: .black.opacity(0.05), radius: 6)
                )
        }
    }
}

struct ToggleRow: View {
    let label: String
    @Binding var isOn: Bool

    var body: some View {
        HStack {
            Text(label)
                .font(.system(size: 16))
            Spacer()
            Toggle("", isOn: $isOn)
                .tint(Color("AccentTeal"))
        }
        .padding(.horizontal, 12)
        .padding(.vertical, 10)
        .background(
            RoundedRectangle(cornerRadius: 10)
                .fill(Color.white)
                .shadow(color: .black.opacity(0.05), radius: 6)
        )
    }
}

struct DatePickerRow: View {
    let label: String
    @Binding var date: Date

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(label)
                .font(.system(size: 14))
                .foregroundColor(.gray)
            DatePicker("", selection: $date, displayedComponents: .date)
                .labelsHidden()
                .padding(.horizontal, 12)
                .padding(.vertical, 10)
                .background(
                    RoundedRectangle(cornerRadius: 10)
                        .fill(Color.white)
                        .shadow(color: .black.opacity(0.05), radius: 6)
                )
        }
    }
}

// MARK: - Permissions Page
struct PermissionsPage: View {
    @State private var healthGranted = false
    @State private var notificationsGranted = false
    @State private var locationGranted = false
    
    var body: some View {
        VStack(spacing: 32) {
            Spacer()
            
            Text("授予权限，助我更好地帮助你")
                .font(.system(size: 28, weight: .bold))
            
            Text("请授予必要权限，以便提供个性化支持与洞见。")
                .font(.system(size: 16))
                .foregroundColor(.gray)
                .multilineTextAlignment(.center)
            
            VStack(spacing: 16) {
                PermissionRow(
                    icon: "heart.fill",
                    iconColor: .red,
                    title: "健康数据",
                    description: "访问血糖、心率与活动数据",
                    isGranted: $healthGranted
                )
                
                PermissionRow(
                    icon: "bell.fill",
                    iconColor: .orange,
                    title: "通知",
                    description: "接收支持性提醒与通知",
                    isGranted: $notificationsGranted
                )
                
                PermissionRow(
                    icon: "location.fill",
                    iconColor: .blue,
                    title: "定位",
                    description: "启用情境感知支持",
                    isGranted: $locationGranted
                )
            }
            .padding(.horizontal, 20)
            
            Spacer()
            Spacer()
        }
        .padding(.horizontal, 20)
    }
}

struct PermissionRow: View {
    let icon: String
    let iconColor: Color
    let title: String
    let description: String
    @Binding var isGranted: Bool
    
    var body: some View {
        HStack(spacing: 16) {
            Image(systemName: icon)
                .font(.system(size: 24))
                .foregroundColor(iconColor)
                .frame(width: 50)
            
            VStack(alignment: .leading, spacing: 4) {
                Text(title)
                    .font(.system(size: 16, weight: .semibold))
                
                Text(description)
                    .font(.system(size: 13))
                    .foregroundColor(.gray)
            }
            
            Spacer()
            
            Button(action: { isGranted.toggle() }) {
                Image(systemName: isGranted ? "checkmark.circle.fill" : "circle")
                    .font(.system(size: 28))
                    .foregroundColor(isGranted ? Color("AccentTeal") : .gray.opacity(0.3))
            }
        }
        .padding(16)
        .background(
            RoundedRectangle(cornerRadius: 16)
                .fill(Color.white)
                .shadow(color: .black.opacity(0.05), radius: 10)
        )
    }
}

// MARK: - Connect Device Page
struct ConnectDevicePage: View {
    @State private var isSearching = false
    @State private var deviceFound = false
    
    var body: some View {
        VStack(spacing: 32) {
            Spacer()
            
            Text("连接你的 CGM")
                .font(.system(size: 28, weight: .bold))
            
            Text("连接你的连续血糖监测设备，以获取实时洞见。")
                .font(.system(size: 16))
                .foregroundColor(.gray)
                .multilineTextAlignment(.center)
            
            // CGM Options
            VStack(spacing: 16) {
                DeviceOptionButton(
                    icon: "waveform.path.ecg",
                    title: "Dexcom",
                    isConnected: deviceFound
                ) {
                    withAnimation {
                        isSearching = true
                        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                            isSearching = false
                            deviceFound = true
                        }
                    }
                }
                
                DeviceOptionButton(
                    icon: "waveform.path.ecg",
                    title: "FreeStyle Libre",
                    isConnected: false
                ) {}
                
                DeviceOptionButton(
                    icon: "waveform.path.ecg",
                    title: "其他 CGM",
                    isConnected: false
                ) {}
            }
            .padding(.horizontal, 20)
            
            if isSearching {
                HStack(spacing: 8) {
                    ProgressView()
                    Text("正在搜索设备...")
                        .font(.system(size: 14))
                        .foregroundColor(.gray)
                }
            }
            
            Button(action: {}) {
                Text("暂时跳过")
                    .font(.system(size: 14))
                    .foregroundColor(.gray)
            }
            
            Spacer()
            Spacer()
        }
        .padding(.horizontal, 20)
    }
}

struct DeviceOptionButton: View {
    let icon: String
    let title: String
    let isConnected: Bool
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 16) {
                Image(systemName: icon)
                    .font(.system(size: 24))
                    .foregroundColor(Color("AccentTeal"))
                    .frame(width: 50)
                
                Text(title)
                    .font(.system(size: 16, weight: .medium))
                    .foregroundColor(.primary)
                
                Spacer()
                
                if isConnected {
                    Image(systemName: "checkmark.circle.fill")
                        .foregroundColor(.green)
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
    }
}

// MARK: - Completion Page
struct CompletionPage: View {
    var body: some View {
        VStack(spacing: 32) {
            Spacer()
            
            // Success icon
            Image(systemName: "checkmark.circle.fill")
                .font(.system(size: 80))
                .foregroundColor(Color("AccentTeal"))
            
            Text("一切就绪！")
                .font(.system(size: 28, weight: .bold))
            
            Text("我会在你的旅程中一直支持你。记住，我始终与你同行。")
                .font(.system(size: 16))
                .foregroundColor(.gray)
                .multilineTextAlignment(.center)
            
            // Feature highlights
            VStack(alignment: .leading, spacing: 16) {
                FeatureHighlight(icon: "heart.fill", text: "在你需要时提供情感支持")
                FeatureHighlight(icon: "chart.line.uptrend.xyaxis", text: "个性化洞见与报告")
                FeatureHighlight(icon: "sparkles", text: "情境感知的建议")
            }
            .padding(.horizontal, 40)
            
            Spacer()
            Spacer()
        }
        .padding(.horizontal, 40)
    }
}

struct FeatureHighlight: View {
    let icon: String
    let text: String
    
    var body: some View {
        HStack(spacing: 12) {
            Image(systemName: icon)
                .foregroundColor(Color("AccentTeal"))
            
            Text(text)
                .font(.system(size: 15))
                .foregroundColor(.primary)
        }
    }
}

protocol OnboardingDataReceivable: AnyObject {
    func applyOnboardingData(
        height: String,
        weight: String,
        age: String,
        genderIndex: Int,
        diagnosisDate: Date,
        usesMedication: Bool,
        usesInsulin: Bool,
        latestHbA1c: String,
        moodIndex: Int,
        hasStress: Bool
    )
}

#Preview {
    OnboardingView()
        .environmentObject(AppState())
        .environmentObject(HealthManager())
}
