//
//  ReportView.swift
//  WithApp
//
//  Reflective analysis and emotional journey report view
//

import SwiftUI
import Foundation
import Charts
import PhotosUI
import Speech
import AVFoundation

struct ReportView: View {
    @EnvironmentObject var reportViewModel: ReportViewModel
    @State private var selectedTimePeriod: TimePeriod = .day
    @State private var showMoodCurve = true
    @State private var showGlucoseCurve = true
    
    @State private var reflectivePhotoItem: PhotosPickerItem? = nil
    @State private var reflectiveImageData: Data? = nil
    @State private var isRecording = false
    @State private var audioEngine: AVAudioEngine? = nil
    @State private var recognitionRequest: SFSpeechAudioBufferRecognitionRequest?
    @State private var recognitionTask: SFSpeechRecognitionTask?
    @State private var speechRecognizer = SFSpeechRecognizer(locale: Locale(identifier: "zh-CN"))
    @State private var isSpeechAuthorized = false
    @State private var reflectiveText: String = ""
    @State private var reflectiveFeedback: String = ""
    
    // Detect Xcode preview environment to avoid initializing heavy services
    private var isRunningInPreviews: Bool {
        ProcessInfo.processInfo.environment["XCODE_RUNNING_FOR_PREVIEWS"] == "1"
    }
    
    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                // Header
                reportHeader
                
                // Time Period Selector
                timePeriodSelector
                
                // Narrative Summary
                narrativeSummary
                
                if selectedTimePeriod == .day {
                    // Rhythm Chart
                    rhythmChart
                    
                    // Timeline Events
                    timelineEvents
                } else {
                    periodSummary
                }
                
                // Reflective Question
                reflectiveQuestion
                
                // Insights (default expanded)
                insightsSection
            }
            .padding(.horizontal, 20)
            .padding(.vertical, 16)
        }
        .background(Color(red: 0.97, green: 0.98, blue: 0.99))
        .onAppear {
            if isRunningInPreviews {
                isSpeechAuthorized = true
                return
            }
            SFSpeechRecognizer.requestAuthorization { authStatus in
                switch authStatus {
                case .authorized:
                    isSpeechAuthorized = true
                default:
                    isSpeechAuthorized = false
                }
            }
        }
    }
    
    // MARK: - Header
    private var reportHeader: some View {
        HStack {
            Text("今天的一章")
                .font(.system(size: 28, weight: .bold))
            
            Spacer()
            
            Button(action: {}) {
                Image(systemName: "xmark")
                    .foregroundColor(.gray)
                    .padding(12)
                    .background(Circle().fill(Color.gray.opacity(0.1)))
            }
        }
    }
    
    // MARK: - Time Period Selector
    private var timePeriodSelector: some View {
        HStack(spacing: 0) {
            ForEach(TimePeriod.allCases, id: \.self) { period in
                Button(action: {
                    withAnimation {
                        selectedTimePeriod = period
                    }
                }) {
                    Text(period.displayName)
                        .font(.system(size: 14, weight: .medium))
                        .foregroundColor(selectedTimePeriod == period ? .primary : .gray)
                        .padding(.horizontal, 20)
                        .padding(.vertical, 10)
                        .background(
                            Capsule()
                                .fill(selectedTimePeriod == period ? Color.white : Color.clear)
                                .shadow(color: selectedTimePeriod == period ? .black.opacity(0.05) : .clear, radius: 5)
                        )
                }
            }
        }
        .padding(4)
        .background(
            Capsule()
                .fill(Color.gray.opacity(0.1))
        )
    }
    
    // MARK: - Narrative Summary
    private var narrativeSummary: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("从清晨忙碌到傍晚宁静。")
                .font(.system(size: 16))
                .italic()
                .foregroundColor(.gray)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
    
    // MARK: - Rhythm Chart
    private var rhythmChart: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text("今天感觉如何？")
                        .font(.system(size: 20, weight: .bold))
                    
                    Text("看看情绪与血糖如何一起变化")
                        .font(.system(size: 10, weight: .medium))
                        .foregroundColor(.gray)
                        .tracking(1)
                }
                
                Spacer()
                
                legendToggle(title: "情绪曲线", color: Color("AccentTeal"), isOn: $showMoodCurve)
                legendToggle(title: "血糖 mmol/L", color: .orange, isOn: $showGlucoseCurve)
            }
            
            RhythmDualLineChart(
                data: reportViewModel.rhythmData,
                showMoodCurve: showMoodCurve,
                showGlucoseCurve: showGlucoseCurve
            )
                .frame(height: 200)

            Text("血糖正常区间：3.9 - 10.0 mmol/L")
                .font(.system(size: 11, weight: .medium))
                .foregroundColor(.gray)

            heartRateMoments
        }
        .padding(20)
        .background(
            RoundedRectangle(cornerRadius: 20)
                .fill(Color.white)
                .shadow(color: .black.opacity(0.05), radius: 10)
        )
    }
    
    // MARK: - Timeline Events
    private var timelineEvents: some View {
        VStack(spacing: 16) {
            ForEach(reportViewModel.timelineEvents) { event in
                TimelineEventCard(event: event)
            }
        }
    }

    private var periodSummary: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text(selectedTimePeriod == .week ? "本周概览" : "本月概览")
                .font(.system(size: 18, weight: .semibold))

            HStack(spacing: 12) {
                summaryCard(
                    title: "平均血糖",
                    value: String(format: "%.1f mmol/L", reportViewModel.averageGlucoseMmol(for: selectedTimePeriod)),
                    icon: "drop.fill"
                )
                summaryCard(
                    title: "达标时间",
                    value: "\(Int(reportViewModel.timeInRange(for: selectedTimePeriod)))%",
                    icon: "checkmark.seal.fill"
                )
            }

            HStack(spacing: 12) {
                summaryCard(
                    title: "情绪稳定度",
                    value: "\(Int(reportViewModel.moodStability(for: selectedTimePeriod)))%",
                    icon: "face.smiling.fill"
                )
                summaryCard(
                    title: "心率异常提醒",
                    value: "\(reportViewModel.elevatedHeartRateCount(for: selectedTimePeriod)) 次",
                    icon: "heart.fill"
                )
            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
    
    // MARK: - Reflective Question
    private var reflectiveQuestion: some View {
        VStack(spacing: 16) {
            Image(systemName: "sparkles")
                .font(.system(size: 24))
                .foregroundColor(.orange)
            
            Text("你今天午餐吃了什么？")
                .font(.system(size: 18, weight: .medium))
                .foregroundColor(.orange)
                .multilineTextAlignment(.center)
            
            TextField("点此分享...", text: $reflectiveText)
                .textFieldStyle(.plain)
                .padding(.horizontal, 14)
                .padding(.vertical, 10)
                .background(
                    RoundedRectangle(cornerRadius: 12)
                        .fill(Color.white.opacity(0.85))
                )
                .foregroundColor(.orange)
                .onChange(of: reflectiveText) { _, newValue in
                    reflectiveFeedback = generateReflectiveFeedback(from: newValue)
                }
            
            PhotosPicker(selection: $reflectivePhotoItem, matching: .images, photoLibrary: .shared()) {
                Text("选择一张照片")
                    .font(.system(size: 14))
                    .foregroundColor(.orange.opacity(0.7))
            }
            .padding(.top, -10)
            .onChange(of: reflectivePhotoItem) { oldItem, newItem in
                Task {
                    if let newItem = newItem {
                        if let data = try? await newItem.loadTransferable(type: Data.self) {
                            reflectiveImageData = data
                        }
                    }
                }
            }
            
            if let imageData = reflectiveImageData, let uiImage = UIImage(data: imageData) {
                Image(uiImage: uiImage)
                    .resizable()
                    .scaledToFit()
                    .frame(maxHeight: 200)
                    .cornerRadius(20)
                    .onAppear {
                        if reflectiveFeedback.isEmpty {
                            reflectiveFeedback = "收到照片了。可以先记录这顿饭的大致份量，稍后会更容易回顾。"
                        }
                    }
            }
            
            HStack(spacing: 24) {
                Button(action: toggleRecording) {
                    Image(systemName: isRecording ? "mic.fill" : "mic")
                        .font(.system(size: 24))
                        .foregroundColor(.gray)
                }
                
                Button(action: {}) {
                    Image(systemName: "photo")
                        .foregroundColor(.gray)
                }
            }
            
            if !reflectiveText.isEmpty {
                Text(reflectiveText)
                    .font(.system(size: 14))
                    .foregroundColor(.orange)
                    .padding(.top, 8)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }

            if !reflectiveFeedback.isEmpty {
                HStack(alignment: .top, spacing: 8) {
                    Image(systemName: "lightbulb.fill")
                        .foregroundColor(.orange)
                    Text(reflectiveFeedback)
                        .font(.system(size: 13))
                        .foregroundColor(.orange)
                }
                .padding(10)
                .frame(maxWidth: .infinity, alignment: .leading)
                .background(
                    RoundedRectangle(cornerRadius: 12)
                        .fill(Color.white.opacity(0.7))
                )
            }
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 24)
        .padding(.horizontal, 20)
        .background(
            RoundedRectangle(cornerRadius: 20)
                .fill(Color.orange.opacity(0.1))
        )
    }
    
    // MARK: - Insights (Expanded)
    private var insightsSection: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("今日洞见")
                .font(.system(size: 18, weight: .semibold))

            ForEach(reportViewModel.insights) { insight in
                InsightCard(insight: insight)
            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }

    private var heartRateMoments: some View {
        let events = reportViewModel.rhythmData.filter { $0.heartRate != nil }
        return VStack(alignment: .leading, spacing: 8) {
            if !events.isEmpty {
                Text("心率相关时刻")
                    .font(.system(size: 12, weight: .semibold))
                    .foregroundColor(.gray)

                ForEach(events) { point in
                    HStack(spacing: 6) {
                        Image(systemName: "heart.fill")
                            .font(.system(size: 11))
                            .foregroundColor(.pink)
                        Text("\(point.label) · \(point.heartRate ?? 0) bpm")
                            .font(.system(size: 11))
                            .foregroundColor(.gray)
                    }
                }
            }
        }
    }

    private func legendToggle(title: String, color: Color, isOn: Binding<Bool>) -> some View {
        Button(action: { isOn.wrappedValue.toggle() }) {
            HStack(spacing: 6) {
                Circle()
                    .fill(color)
                    .frame(width: 8, height: 8)
                Text(title)
                    .font(.system(size: 10, weight: .medium))
                    .foregroundColor(.gray)
                Image(systemName: isOn.wrappedValue ? "eye.fill" : "eye.slash.fill")
                    .font(.system(size: 10))
                    .foregroundColor(.gray)
            }
            .padding(.horizontal, 8)
            .padding(.vertical, 6)
            .background(Capsule().fill(Color.gray.opacity(0.08)))
        }
        .buttonStyle(.plain)
    }

    private func summaryCard(title: String, value: String, icon: String) -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Image(systemName: icon)
                .font(.system(size: 12))
                .foregroundColor(Color("AccentTeal"))
            Text(title)
                .font(.system(size: 12))
                .foregroundColor(.gray)
            Text(value)
                .font(.system(size: 16, weight: .semibold))
                .foregroundColor(.primary)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(12)
        .background(
            RoundedRectangle(cornerRadius: 14)
                .fill(Color.white)
                .shadow(color: .black.opacity(0.04), radius: 4)
        )
    }

    private func generateReflectiveFeedback(from text: String) -> String {
        let trimmed = text.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !trimmed.isEmpty else { return "" }
        if trimmed.contains("面") || trimmed.contains("米") || trimmed.contains("饭") {
            return "这餐主食信息很有帮助。可以再补充一下大概份量，后续更容易观察血糖变化。"
        }
        if trimmed.contains("甜") || trimmed.contains("奶茶") || trimmed.contains("饮料") {
            return "已记录到含糖食物/饮品。建议餐后留意 1-2 小时的血糖波动。"
        }
        return "记录已保存。可以再加一句当时心情，便于一起看情绪和血糖关系。"
    }
    
    // MARK: - Recording control
    private func toggleRecording() {
        if isRunningInPreviews {
            // Do nothing in previews to avoid audio engine startup
            return
        }
        if isRecording {
            stopRecording()
        } else {
            startRecording()
        }
    }
    
    private func startRecording() {
        guard isSpeechAuthorized else { return }
        
        if audioEngine == nil { audioEngine = AVAudioEngine() }
        guard let audioEngine = audioEngine else { return }
        
        if recognitionTask != nil {
            recognitionTask?.cancel()
            recognitionTask = nil
        }
        
        let audioSession = AVAudioSession.sharedInstance()
        do {
            try audioSession.setCategory(.record, mode: .measurement, options: .duckOthers)
            try audioSession.setActive(true, options: .notifyOthersOnDeactivation)
        } catch {
            print("audioSession properties weren't set because of an error.")
            return
        }
        
        recognitionRequest = SFSpeechAudioBufferRecognitionRequest()
        guard let recognitionRequest = recognitionRequest else {
            print("Unable to create recognition request")
            return
        }
        recognitionRequest.shouldReportPartialResults = true
        
        let inputNode = audioEngine.inputNode
        
        recognitionTask = speechRecognizer?.recognitionTask(with: recognitionRequest) { result, error in
            if let result = result {
                reflectiveText = result.bestTranscription.formattedString
            }
            
            if error != nil || (result?.isFinal ?? false) {
                stopRecording()
            }
        }
        
        let recordingFormat = inputNode.outputFormat(forBus: 0)
        inputNode.removeTap(onBus: 0)
        inputNode.installTap(onBus: 0, bufferSize: 1024, format: recordingFormat) { buffer, _ in
            recognitionRequest.append(buffer)
        }
        
        audioEngine.prepare()
        do {
            try audioEngine.start()
            isRecording = true
        } catch {
            print("audioEngine couldn't start because of an error.")
        }
    }
    
    private func stopRecording() {
        audioEngine?.stop()
        recognitionRequest?.endAudio()
        audioEngine?.inputNode.removeTap(onBus: 0)
        isRecording = false
        recognitionRequest = nil
        recognitionTask = nil
    }
}

private struct InsightCard: View {
    let insight: Insight

    var body: some View {
        HStack(alignment: .top, spacing: 10) {
            Image(systemName: insight.icon)
                .foregroundColor(Color("AccentTeal"))
                .frame(width: 18)
            VStack(alignment: .leading, spacing: 4) {
                Text(insight.title)
                    .font(.system(size: 14, weight: .semibold))
                Text(insight.description)
                    .font(.system(size: 12))
                    .foregroundColor(.gray)
            }
            Spacer()
        }
        .padding(12)
        .background(
            RoundedRectangle(cornerRadius: 12)
                .fill(Color.white)
                .shadow(color: .black.opacity(0.04), radius: 4)
        )
    }
}

// MARK: - Time Period Enum
enum TimePeriod: String, CaseIterable {
    case day = "日"
    case week = "周"
    case month = "月"
    
    var displayName: String { rawValue }
}

// MARK: - Dual Rhythm Chart
struct RhythmDualLineChart: View {
    let data: [RhythmDataPoint]
    let showMoodCurve: Bool
    let showGlucoseCurve: Bool

    var body: some View {
        Chart {
            if showMoodCurve {
                ForEach(data) { point in
                    LineMark(
                        x: .value("时间", point.timestamp),
                        y: .value("情绪", point.moodScore)
                    )
                    .interpolationMethod(.catmullRom)
                    .foregroundStyle(Color("AccentTeal"))
                    .lineStyle(StrokeStyle(lineWidth: 3))
                }
            }

            if showGlucoseCurve {
                ForEach(data) { point in
                    LineMark(
                        x: .value("时间", point.timestamp),
                        y: .value("血糖", point.glucoseMmol * 10)
                    )
                    .interpolationMethod(.catmullRom)
                    .foregroundStyle(.orange)
                    .lineStyle(StrokeStyle(lineWidth: 2, dash: [6, 4]))
                }
            }

            ForEach(data.filter { $0.heartRate != nil }) { point in
                PointMark(
                    x: .value("时间", point.timestamp),
                    y: .value("情绪", point.moodScore)
                )
                .foregroundStyle(.pink)
                .annotation(position: .top) {
                    HStack(spacing: 2) {
                        Image(systemName: "heart.fill")
                        Text("\(point.heartRate ?? 0)")
                    }
                    .font(.system(size: 9))
                    .foregroundColor(.pink)
                }
            }
        }
        .chartXAxis {
            AxisMarks(values: .automatic(desiredCount: 4)) { _ in
                AxisGridLine(stroke: StrokeStyle(lineWidth: 0.3))
                AxisTick()
                AxisValueLabel(format: .dateTime.hour())
            }
        }
        .chartYAxis {
            AxisMarks(values: [20, 40, 60, 80]) { _ in
                AxisGridLine(stroke: StrokeStyle(lineWidth: 0.3))
                AxisTick()
            }
        }
        .chartYScale(domain: 0...100)
    }
}

// MARK: - Timeline Event Card
struct TimelineEventCard: View {
    let event: TimelineEvent
    
    var body: some View {
        HStack(alignment: .top, spacing: 16) {
            // Time indicator
            VStack {
                Text(event.time)
                    .font(.system(size: 12, weight: .medium))
                    .foregroundColor(Color("AccentTeal"))
            }
            .frame(width: 50)
            
            // Event content
            VStack(alignment: .leading, spacing: 8) {
                HStack {
                    Text(event.title)
                        .font(.system(size: 16, weight: .semibold))
                    
                    if let location = event.location {
                        HStack(spacing: 4) {
                            Image(systemName: "location.fill")
                                .font(.system(size: 10))
                            Text(location)
                                .font(.system(size: 12))
                        }
                        .foregroundColor(.gray)
                    }
                    
                    Spacer()
                    
                    if let glucose = event.glucoseValue {
                        VStack(alignment: .trailing, spacing: 2) {
                            Text(String(format: "%.1f mmol/L", glucose / 18.0))
                                .font(.system(size: 13, weight: .bold))
                                .foregroundColor(Color("AccentTeal"))
                            Text("正常 3.9-10.0")
                                .font(.system(size: 10))
                                .foregroundColor(.gray)
                        }
                    }
                }
                
                Text(event.description)
                    .font(.system(size: 14))
                    .foregroundColor(.gray)
                
                if let mood = event.mood, let emoji = event.emoji {
                    HStack(spacing: 8) {
                        Text("MOOD NOTE")
                            .font(.system(size: 10, weight: .medium))
                            .foregroundColor(.orange)
                        
                        Text("\"\(mood)\" \(emoji)")
                            .font(.system(size: 14))
                    }
                    .padding(.horizontal, 12)
                    .padding(.vertical, 8)
                    .background(
                        RoundedRectangle(cornerRadius: 10)
                            .fill(Color.orange.opacity(0.1))
                    )
                }
            }
            .padding(16)
            .background(
                RoundedRectangle(cornerRadius: 16)
                    .fill(Color.white)
                    .shadow(color: .black.opacity(0.05), radius: 5)
            )
        }
    }
}

#Preview {
    ReportView()
        .environmentObject(ReportViewModel())
}
