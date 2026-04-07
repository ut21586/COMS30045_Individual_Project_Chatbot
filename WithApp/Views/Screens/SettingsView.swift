//
//  SettingsView.swift
//  WithApp
//
//  Settings page styled as grouped cards from design reference
//

import SwiftUI

struct SettingsView: View {
    @EnvironmentObject var appState: AppState
    @Environment(\.dismiss) var dismiss

    @State private var showCGMConnection = false
    @State private var showCharacterSelection = false
    @State private var editingField: EditableHealthField?
    @State private var draftTextValue = ""
    @State private var draftHeight = ""
    @State private var draftWeight = ""
    @State private var draftDate = Date()
    @State private var draftBoolValue = false

    @AppStorage("settings_gender") private var gender = "男"
    @AppStorage("settings_age") private var age = ""
    @AppStorage("settings_height_cm") private var heightCm = ""
    @AppStorage("settings_weight_kg") private var weightKg = ""
    @AppStorage("settings_diagnosis_time") private var diagnosisTime = Date().timeIntervalSince1970
    @AppStorage("settings_uses_oral_medication") private var usesOralMedication = true
    @AppStorage("settings_uses_insulin") private var usesInsulin = true
    @AppStorage("settings_hba1c") private var hbA1c = ""

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 18) {
                headerView
                profileCard
                healthDataSection
                deviceSection
                generalSection
                privacySection
                logoutButton
            }
            .padding(.horizontal, 12)
            .padding(.vertical, 12)
        }
        .background(Color(red: 0.94, green: 0.95, blue: 0.96).ignoresSafeArea())
        .sheet(isPresented: $showCGMConnection) {
            CGMConnectionView()
        }
        .sheet(isPresented: $showCharacterSelection) {
            CharacterSelectionView()
        }
        .sheet(item: $editingField) { field in
            healthEditorSheet(for: field)
        }
    }

    private var headerView: some View {
        HStack(spacing: 12) {
            Button(action: { dismiss() }) {
                Image(systemName: "chevron.left")
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundColor(.black.opacity(0.8))
                    .frame(width: 28, height: 28)
            }

            Text("设置")
                .font(.system(size: 38, weight: .bold))
                .foregroundColor(.black)

            Spacer()
        }
    }

    private var profileCard: some View {
        HStack(spacing: 14) {
            Circle()
                .fill(Color("AccentTeal").opacity(0.12))
                .frame(width: 64, height: 64)
                .overlay(
                    Image(systemName: "desktopcomputer")
                        .font(.system(size: 30, weight: .medium))
                        .foregroundColor(Color("AccentTeal"))
                )

            VStack(alignment: .leading, spacing: 4) {
                Text(appState.userName)
                    .font(.system(size: 31, weight: .semibold))
                    .foregroundColor(.black.opacity(0.85))

                Text("与 With 同行")
                    .font(.system(size: 16))
                    .foregroundColor(.gray)
            }

            Spacer()
        }
        .padding(14)
        .background(cardBackground)
    }

    private var healthDataSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            sectionTitle("我的健康数据")
            cardContainer {
                SettingsValueRow(title: "性别", value: gender, action: { beginEditing(.gender) })
                Divider().padding(.leading, 16)
                SettingsValueRow(title: "年龄", value: ageDisplay, action: { beginEditing(.age) })
                Divider().padding(.leading, 16)
                SettingsValueRow(title: "身高 / 体重", value: heightWeightDisplay, action: { beginEditing(.heightWeight) })
                Divider().padding(.leading, 16)
                SettingsValueRow(title: "确诊时间", value: diagnosisDisplay, action: { beginEditing(.diagnosisTime) })
                Divider().padding(.leading, 16)
                SettingsValueRow(title: "使用口服药", value: usesOralMedication ? "是" : "否", action: { beginEditing(.oralMedication) })
                Divider().padding(.leading, 16)
                SettingsValueRow(title: "使用胰岛素", value: usesInsulin ? "是" : "否", action: { beginEditing(.insulin) })
                Divider().padding(.leading, 16)
                SettingsValueRow(title: "近期糖化血红蛋白", value: hbA1cDisplay, action: { beginEditing(.hbA1c) })
            }
        }
    }

    private var deviceSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            sectionTitle("设备")
            cardContainer {
                SettingsIconRow(
                    icon: "heart.fill",
                    iconColor: .blue,
                    iconBackground: Color.blue.opacity(0.14),
                    title: "CGM 设备",
                    trailingText: appState.cgmConnected ? "已连接" : "未连接",
                    action: { showCGMConnection = true }
                )
                Divider().padding(.leading, 56)
                SettingsIconRow(
                    icon: "checkmark.circle.fill",
                    iconColor: .orange,
                    iconBackground: Color.orange.opacity(0.2),
                    title: "智能手表",
                    trailingText: "已连接",
                    action: {}
                )
                Divider().padding(.leading, 56)
                SettingsIconRow(
                    icon: "phone.fill",
                    iconColor: .gray,
                    iconBackground: Color.gray.opacity(0.18),
                    title: "手机健康",
                    trailingText: "同步已启用",
                    action: {}
                )
            }
        }
    }

    private var generalSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            sectionTitle("通用设置")
            cardContainer {
                SettingsIconRow(
                    icon: "person.fill",
                    iconColor: Color("AccentTeal"),
                    iconBackground: Color("AccentTeal").opacity(0.16),
                    title: "角色",
                    trailingText: appState.selectedCharacter.displayName,
                    action: { showCharacterSelection = true }
                )
                Divider().padding(.leading, 56)
                SettingsToggleIconRow(
                    icon: "bell.fill",
                    iconColor: .orange,
                    iconBackground: Color.orange.opacity(0.2),
                    title: "推送通知",
                    isOn: $appState.notificationsEnabled
                )
            }
        }
    }

    private var privacySection: some View {
        VStack(alignment: .leading, spacing: 8) {
            sectionTitle("隐私与数据")
            cardContainer {
                SettingsIconRow(
                    icon: "lock.fill",
                    iconColor: .blue,
                    iconBackground: Color.blue.opacity(0.15),
                    title: "数据权限",
                    trailingText: "",
                    action: {}
                )
                Divider().padding(.leading, 56)
                SettingsIconRow(
                    icon: "trash.fill",
                    iconColor: .red,
                    iconBackground: Color.red.opacity(0.15),
                    title: "删除所有数据",
                    trailingText: "",
                    action: {}
                )
                Divider().padding(.leading, 56)
                SettingsIconRow(
                    icon: "info.circle.fill",
                    iconColor: .gray,
                    iconBackground: Color.gray.opacity(0.2),
                    title: "隐私政策",
                    trailingText: "",
                    action: {}
                )
            }
        }
    }

    private var logoutButton: some View {
        Button(action: {}) {
            Text("退出登录")
                .font(.system(size: 30, weight: .semibold))
                .foregroundColor(.red)
                .frame(maxWidth: .infinity)
                .padding(.vertical, 14)
                .background(cardBackground)
        }
        .padding(.top, 4)
        .padding(.bottom, 20)
    }

    private func sectionTitle(_ text: String) -> some View {
        Text(text)
            .font(.system(size: 15, weight: .semibold))
            .foregroundColor(.gray)
            .padding(.leading, 2)
    }

    private func cardContainer<Content: View>(@ViewBuilder content: () -> Content) -> some View {
        VStack(spacing: 0) {
            content()
        }
        .background(cardBackground)
    }

    private var cardBackground: some View {
        RoundedRectangle(cornerRadius: 16)
            .fill(Color.white)
            .shadow(color: .black.opacity(0.03), radius: 8, y: 2)
    }

    private var ageDisplay: String {
        age.isEmpty ? "岁" : "\(age)岁"
    }

    private var heightWeightDisplay: String {
        let h = heightCm.isEmpty ? "cm" : "\(heightCm)cm"
        let w = weightKg.isEmpty ? "kg" : "\(weightKg)kg"
        return "\(h) / \(w)"
    }

    private var diagnosisDisplay: String {
        diagnosisFormatter.string(from: Date(timeIntervalSince1970: diagnosisTime))
    }

    private var hbA1cDisplay: String {
        hbA1c.isEmpty ? "%" : "\(hbA1c)%"
    }

    private func beginEditing(_ field: EditableHealthField) {
        switch field {
        case .gender:
            draftTextValue = gender
        case .age:
            draftTextValue = age
        case .heightWeight:
            draftHeight = heightCm
            draftWeight = weightKg
        case .diagnosisTime:
            draftDate = Date(timeIntervalSince1970: diagnosisTime)
        case .oralMedication:
            draftBoolValue = usesOralMedication
        case .insulin:
            draftBoolValue = usesInsulin
        case .hbA1c:
            draftTextValue = hbA1c
        }
        editingField = field
    }

    @ViewBuilder
    private func healthEditorSheet(for field: EditableHealthField) -> some View {
        NavigationStack {
            Form {
                switch field {
                case .gender:
                    Picker("性别", selection: $draftTextValue) {
                        Text("男").tag("男")
                        Text("女").tag("女")
                        Text("其他").tag("其他")
                    }
                    .pickerStyle(.inline)
                case .age:
                    TextField("请输入年龄", text: $draftTextValue)
                        .keyboardType(.numberPad)
                case .heightWeight:
                    TextField("身高(cm)", text: $draftHeight)
                        .keyboardType(.decimalPad)
                    TextField("体重(kg)", text: $draftWeight)
                        .keyboardType(.decimalPad)
                case .diagnosisTime:
                    DatePicker("确诊时间", selection: $draftDate, displayedComponents: [.date])
                        .datePickerStyle(.graphical)
                case .oralMedication:
                    Toggle("是否使用口服药", isOn: $draftBoolValue)
                case .insulin:
                    Toggle("是否使用胰岛素", isOn: $draftBoolValue)
                case .hbA1c:
                    TextField("最近一次 HbA1c(%)", text: $draftTextValue)
                        .keyboardType(.decimalPad)
                }
            }
            .navigationTitle(field.title)
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .cancellationAction) {
                    Button("取消") {
                        editingField = nil
                    }
                }
                ToolbarItem(placement: .confirmationAction) {
                    Button("保存") {
                        saveEditing(field)
                        editingField = nil
                    }
                }
            }
        }
    }

    private func saveEditing(_ field: EditableHealthField) {
        switch field {
        case .gender:
            gender = draftTextValue
        case .age:
            age = draftTextValue
        case .heightWeight:
            heightCm = draftHeight
            weightKg = draftWeight
        case .diagnosisTime:
            diagnosisTime = draftDate.timeIntervalSince1970
        case .oralMedication:
            usesOralMedication = draftBoolValue
        case .insulin:
            usesInsulin = draftBoolValue
        case .hbA1c:
            hbA1c = draftTextValue
        }
    }
}

private struct SettingsValueRow: View {
    let title: String
    let value: String
    var action: (() -> Void)? = nil

    var body: some View {
        Group {
            if let action {
                Button(action: action) {
                    rowContent
                }
                .buttonStyle(.plain)
            } else {
                rowContent
            }
        }
    }

    private var rowContent: some View {
        HStack {
            Text(title)
                .font(.system(size: 16))
                .foregroundColor(.black.opacity(0.78))

            Spacer()

            Text(value)
                .font(.system(size: 16))
                .foregroundColor(.gray)

            Image(systemName: "chevron.right")
                .font(.system(size: 16, weight: .medium))
                .foregroundColor(.gray.opacity(0.7))
        }
        .padding(.horizontal, 14)
        .padding(.vertical, 14)
    }
}

private enum EditableHealthField: String, Identifiable {
    case gender
    case age
    case heightWeight
    case diagnosisTime
    case oralMedication
    case insulin
    case hbA1c

    var id: String { rawValue }

    var title: String {
        switch self {
        case .gender: return "性别"
        case .age: return "年龄"
        case .heightWeight: return "身高 / 体重"
        case .diagnosisTime: return "确诊时间"
        case .oralMedication: return "使用口服药"
        case .insulin: return "使用胰岛素"
        case .hbA1c: return "近期糖化血红蛋白"
        }
    }
}

private let diagnosisFormatter: DateFormatter = {
    let formatter = DateFormatter()
    formatter.locale = Locale(identifier: "zh_Hans")
    formatter.dateFormat = "yyyy年MM月"
    return formatter
}()

private struct SettingsIconRow: View {
    let icon: String
    let iconColor: Color
    let iconBackground: Color
    let title: String
    let trailingText: String
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            HStack(spacing: 12) {
                Image(systemName: icon)
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundColor(iconColor)
                    .frame(width: 32, height: 32)
                    .background(iconBackground)
                    .clipShape(RoundedRectangle(cornerRadius: 9))

                Text(title)
                    .font(.system(size: 16, weight: .medium))
                    .foregroundColor(.black.opacity(0.8))

                Spacer()

                if !trailingText.isEmpty {
                    Text(trailingText)
                        .font(.system(size: 15))
                        .foregroundColor(.gray)
                }

                Image(systemName: "chevron.right")
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(.gray.opacity(0.7))
            }
            .padding(.horizontal, 12)
            .padding(.vertical, 12)
        }
    }
}

private struct SettingsToggleIconRow: View {
    let icon: String
    let iconColor: Color
    let iconBackground: Color
    let title: String
    @Binding var isOn: Bool

    var body: some View {
        HStack(spacing: 12) {
            Image(systemName: icon)
                .font(.system(size: 18, weight: .semibold))
                .foregroundColor(iconColor)
                .frame(width: 32, height: 32)
                .background(iconBackground)
                .clipShape(RoundedRectangle(cornerRadius: 9))

            Text(title)
                .font(.system(size: 16, weight: .medium))
                .foregroundColor(.black.opacity(0.8))

            Spacer()

            Toggle("", isOn: $isOn)
                .labelsHidden()
                .tint(Color("AccentTeal"))
        }
        .padding(.horizontal, 12)
        .padding(.vertical, 12)
    }
}

// Kept public because OnboardingView reuses this helper.
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

#Preview {
    SettingsView()
        .environmentObject(AppState())
}
