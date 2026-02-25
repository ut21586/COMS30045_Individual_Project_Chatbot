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
    @State private var showInsights = false
    
    @State private var reflectivePhotoItem: PhotosPickerItem? = nil
    @State private var reflectiveImageData: Data? = nil
    @State private var isRecording = false
    @State private var audioEngine: AVAudioEngine? = nil
    @State private var recognitionRequest: SFSpeechAudioBufferRecognitionRequest?
    @State private var recognitionTask: SFSpeechRecognitionTask?
    @State private var speechRecognizer = SFSpeechRecognizer(locale: Locale(identifier: "zh-CN"))
    @State private var isSpeechAuthorized = false
    @State private var reflectiveText: String = ""
    
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
                
                // Rhythm Chart
                rhythmChart
                
                // Timeline Events
                timelineEvents
                
                // Reflective Question
                reflectiveQuestion
                
                // Insights Button
                insightsButton
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
                    Text("今日节律")
                        .font(.system(size: 20, weight: .bold))
                    
                    Text("你的身体之歌")
                        .font(.system(size: 10, weight: .medium))
                        .foregroundColor(.gray)
                        .tracking(1)
                }
                
                Spacer()
                
                // Legend
                HStack(spacing: 8) {
                    Circle()
                        .fill(Color("AccentTeal"))
                        .frame(width: 8, height: 8)
                    Text("能量流")
                        .font(.system(size: 10))
                        .foregroundColor(.gray)
                }
            }
            
            // Chart
            EnergyFlowChart(data: reportViewModel.energyData)
                .frame(height: 200)
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
            
            Button(action: {}) {
                Text("点此分享...")
                    .font(.system(size: 14))
                    .foregroundColor(.orange.opacity(0.7))
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
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 24)
        .padding(.horizontal, 20)
        .background(
            RoundedRectangle(cornerRadius: 20)
                .fill(Color.orange.opacity(0.1))
        )
    }
    
    // MARK: - Insights Button
    private var insightsButton: some View {
        Button(action: { showInsights = true }) {
            HStack {
                Text("发现洞见")
                    .font(.system(size: 16, weight: .semibold))
                Image(systemName: "chevron.down")
            }
            .foregroundColor(.white)
            .padding(.horizontal, 24)
            .padding(.vertical, 14)
            .background(
                Capsule()
                    .fill(Color.black.opacity(0.8))
            )
        }
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

// MARK: - Time Period Enum
enum TimePeriod: String, CaseIterable {
    case day = "日"
    case week = "周"
    case month = "月"
    
    var displayName: String { rawValue }
}

// MARK: - Energy Flow Chart
struct EnergyFlowChart: View {
    let data: [EnergyDataPoint]
    
    var body: some View {
        GeometryReader { geometry in
            ZStack {
                // Background gradient area
                Path { path in
                    guard !data.isEmpty else { return }
                    
                    let width = geometry.size.width
                    let height = geometry.size.height
                    let stepX = width / CGFloat(data.count - 1)
                    
                    path.move(to: CGPoint(x: 0, y: height))
                    
                    for (index, point) in data.enumerated() {
                        let x = CGFloat(index) * stepX
                        let y = height - (CGFloat(point.value) / 100 * height)
                        
                        if index == 0 {
                            path.addLine(to: CGPoint(x: x, y: y))
                        } else {
                            let prevX = CGFloat(index - 1) * stepX
                            let prevY = height - (CGFloat(data[index - 1].value) / 100 * height)
                            let controlX = (prevX + x) / 2
                            path.addCurve(
                                to: CGPoint(x: x, y: y),
                                control1: CGPoint(x: controlX, y: prevY),
                                control2: CGPoint(x: controlX, y: y)
                            )
                        }
                    }
                    
                    path.addLine(to: CGPoint(x: width, y: height))
                    path.closeSubpath()
                }
                .fill(
                    LinearGradient(
                        colors: [Color("AccentTeal").opacity(0.4), Color("AccentTeal").opacity(0.1)],
                        startPoint: .top,
                        endPoint: .bottom
                    )
                )
                
                // Line
                Path { path in
                    guard !data.isEmpty else { return }
                    
                    let width = geometry.size.width
                    let height = geometry.size.height
                    let stepX = width / CGFloat(data.count - 1)
                    
                    for (index, point) in data.enumerated() {
                        let x = CGFloat(index) * stepX
                        let y = height - (CGFloat(point.value) / 100 * height)
                        
                        if index == 0 {
                            path.move(to: CGPoint(x: x, y: y))
                        } else {
                            let prevX = CGFloat(index - 1) * stepX
                            let prevY = height - (CGFloat(data[index - 1].value) / 100 * height)
                            let controlX = (prevX + x) / 2
                            path.addCurve(
                                to: CGPoint(x: x, y: y),
                                control1: CGPoint(x: controlX, y: prevY),
                                control2: CGPoint(x: controlX, y: y)
                            )
                        }
                    }
                }
                .stroke(Color("AccentTeal"), lineWidth: 3)
                
                // Labels
                VStack {
                    HStack {
                        Text("高能量")
                            .font(.system(size: 10))
                            .foregroundColor(.gray)
                        Spacer()
                    }
                    Spacer()
                    HStack {
                        Text("休息")
                            .font(.system(size: 10))
                            .foregroundColor(.gray)
                        Spacer()
                    }
                }
            }
        }
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
                        Text(String(format: "%.1f", glucose))
                            .font(.system(size: 16, weight: .bold))
                            .foregroundColor(Color("AccentTeal"))
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

