//
//  ChatView.swift
//  WithApp
//
//  Chat interface for supportive conversations with LLM
//

import SwiftUI
import Foundation
import PhotosUI
import Speech
import AVFoundation

struct ChatView: View {
    @EnvironmentObject var appState: AppState
    @EnvironmentObject var chatViewModel: ChatViewModel
    @EnvironmentObject var healthManager: HealthManager
    
    @State private var inputText = ""
    @State private var showEmojiPicker = false
    @State private var isSpeechAuthorized = false
    @State private var isRecording = false
    @State private var audioRecorder: AVAudioRecorder?
    @State private var speechRecognizer: SFSpeechRecognizer? = nil
    @State private var recognitionRequest: SFSpeechAudioBufferRecognitionRequest?
    @State private var recognitionTask: SFSpeechRecognitionTask?
    @State private var audioEngine: AVAudioEngine? = nil
    @FocusState private var isInputFocused: Bool
    @State private var selectedPhotoItem: PhotosPickerItem? = nil
    @State private var selectedImageData: Data? = nil

    // Detect Xcode preview environment to avoid initializing heavy services
    private var isRunningInPreviews: Bool {
        ProcessInfo.processInfo.environment["XCODE_RUNNING_FOR_PREVIEWS"] == "1"
    }
    
    @State private var showSpeechNotAuthorizedAlert = false
    
    var body: some View {
        VStack(spacing: 0) {
            // Header
            chatHeader
            
            // Messages
            messagesScrollView
            
            // Input Area
            inputArea
            
            Group {
                if !isRunningInPreviews {
                    PhotosPicker(
                        selection: $selectedPhotoItem,
                        matching: .images,
                        photoLibrary: .shared()
                    ) {
                        EmptyView()
                    }
                    .onChange(of: selectedPhotoItem) { oldValue, newItem in
                        Task {
                            if let item = newItem {
                                if let data = try? await item.loadTransferable(type: Data.self) {
                                    selectedImageData = data
                                }
                            }
                        }
                    }
                    .onChange(of: selectedImageData) { oldData, newData in
                        if let data = newData {
                            chatViewModel.sendImageMessage(data)
                            selectedImageData = nil
                            selectedPhotoItem = nil
                        }
                    }
                    .frame(width: 0, height: 0)
                    .clipped()
                    .opacity(0)
                }
            }
        }
        .background(Color(red: 0.95, green: 0.97, blue: 0.98))
        .onAppear {
            if isRunningInPreviews {
                // Skip requesting speech authorization and heavy init in previews
                self.isSpeechAuthorized = true
                self.speechRecognizer = nil
                return
            }
            self.speechRecognizer = SFSpeechRecognizer(locale: Locale(identifier: "zh-CN"))
            SFSpeechRecognizer.requestAuthorization { status in
                if status == .authorized {
                    DispatchQueue.main.async { self.isSpeechAuthorized = true }
                } else {
                    DispatchQueue.main.async { self.isSpeechAuthorized = false }
                }
            }
        }
        .alert("语音权限未授权", isPresented: $showSpeechNotAuthorizedAlert) {
            Button("确定") {}
        } message: {
            Text("请在设置中开启语音识别权限以使用语音输入功能。")
        }
    }
    
    // MARK: - Header
    private var chatHeader: some View {
        HStack {
            Button(action: {}) {
                Image(systemName: "chevron.left")
                    .foregroundColor(.gray)
            }
            
            Spacer()
            
            HStack(spacing: 12) {
                // Character avatar
                CharacterAvatar(character: appState.selectedCharacter, size: 40)
                
                VStack(alignment: .leading, spacing: 2) {
                    Text("With")
                        .font(.system(size: 18, weight: .semibold))
                    
                    HStack(spacing: 4) {
                        Circle()
                            .fill(Color("AccentTeal"))
                            .frame(width: 6, height: 6)
                        Text("每日")
                            .font(.system(size: 12))
                            .foregroundColor(Color("AccentTeal"))
                    }
                }
            }
            
            Spacer()
            
            Color.clear
                .frame(width: 24, height: 24)
        }
        .padding(.horizontal, 20)
        .padding(.vertical, 12)
        .background(Color.white)
    }
    
    // MARK: - Messages
    private var messagesScrollView: some View {
        ScrollViewReader { proxy in
            ScrollView {
                LazyVStack(spacing: 16) {
                    // Date Header
                    Text("今天")
                        .font(.system(size: 12))
                        .foregroundColor(.gray)
                        .padding(.vertical, 10)
                    
                    ForEach(chatViewModel.messages) { message in
                        if let imageData = message.imageData, let uiImage = UIImage(data: imageData) {
                            ChatImageBubble(message: message, uiImage: uiImage)
                                .id(message.id)
                        } else {
                            ChatBubble(message: message)
                                .id(message.id)
                        }
                    }
                    
                    if chatViewModel.isTyping {
                        TypingIndicator()
                    }
                }
                .padding(.horizontal, 20)
                .padding(.vertical, 16)
            }
            .onChange(of: chatViewModel.messages.count) { oldValue, newValue in
                withAnimation {
                    if let lastMessage = chatViewModel.messages.last {
                        proxy.scrollTo(lastMessage.id, anchor: .bottom)
                    }
                }
            }
        }
    }
    
    // MARK: - Input Area
    private var inputArea: some View {
        VStack(spacing: 0) {
            Divider()
            
            HStack(spacing: 12) {
                // PhotosPicker replaces the image picker button, so the button action does nothing
                // The PhotosPicker UI is hidden and handled separately
                
                Button(action: {}) {
                    Image(systemName: "photo")
                        .foregroundColor(.gray)
                        .font(.system(size: 20))
                }
                
                // Emoji picker
                Button(action: { showEmojiPicker.toggle() }) {
                    Image(systemName: "face.smiling")
                        .foregroundColor(.gray)
                        .font(.system(size: 20))
                }
                
                // Voice input
                Button(action: {
                    toggleRecording()
                }) {
                    Image(systemName: isRecording ? "mic.fill" : "mic")
                        .foregroundColor(isRecording ? Color("AccentTeal") : .gray)
                        .font(.system(size: 20))
                }
                
                // Text input
                TextField("分享你的感受...", text: $inputText)
                    .textFieldStyle(.plain)
                    .padding(.horizontal, 16)
                    .padding(.vertical, 10)
                    .background(
                        RoundedRectangle(cornerRadius: 20)
                            .fill(Color.gray.opacity(0.1))
                    )
                    .focused($isInputFocused)
                
                // Send button
                Button(action: sendMessage) {
                    Image(systemName: "paperplane.fill")
                        .foregroundColor(inputText.isEmpty ? .gray : Color("AccentTeal"))
                        .font(.system(size: 20))
                }
                .disabled(inputText.isEmpty)
            }
            .padding(.horizontal, 20)
            .padding(.vertical, 12)
            .background(Color.white)
        }
    }
    
    // MARK: - Actions
    private func sendMessage() {
        guard !inputText.trimmingCharacters(in: .whitespaces).isEmpty else { return }
        chatViewModel.sendMessage(inputText)
        inputText = ""
        isInputFocused = false
    }
    
    private func toggleRecording() {
        if isRunningInPreviews {
            // Do nothing in previews
            return
        }
        if !isSpeechAuthorized {
            showSpeechNotAuthorizedAlert = true
            return
        }
        if isRecording {
            stopRecordingAndRecognize()
        } else {
            startRecording()
        }
        isRecording.toggle()
    }
    
    private func startRecording() {
        // Cancel previous task if running
        recognitionTask?.cancel()
        recognitionTask = nil
        
        let audioSession = AVAudioSession.sharedInstance()
        do {
            try audioSession.setCategory(.record, mode: .measurement, options: .duckOthers)
            try audioSession.setActive(true, options: .notifyOthersOnDeactivation)
        } catch {
            print("Audio session properties weren't set because of an error: \(error.localizedDescription)")
            return
        }
        
        recognitionRequest = SFSpeechAudioBufferRecognitionRequest()
        guard let recognitionRequest = recognitionRequest else {
            print("Unable to create an SFSpeechAudioBufferRecognitionRequest object")
            return
        }
        
        recognitionRequest.shouldReportPartialResults = true
        
        if audioEngine == nil { audioEngine = AVAudioEngine() }
        guard let audioEngine = audioEngine else { return }
        let inputNode = audioEngine.inputNode
        
        recognitionTask = speechRecognizer?.recognitionTask(with: recognitionRequest) { result, error in
            var isFinal = false
            
            if let result = result {
                DispatchQueue.main.async {
                    self.inputText = result.bestTranscription.formattedString
                }
                isFinal = result.isFinal
            }
            
            if error != nil || isFinal {
                audioEngine.stop()
                inputNode.removeTap(onBus: 0)
                self.recognitionRequest = nil
                self.recognitionTask = nil
                DispatchQueue.main.async {
                    self.isRecording = false
                }
                if isFinal {
                    DispatchQueue.main.async {
                        self.sendMessage()
                    }
                }
            }
        }
        
        let recordingFormat = inputNode.outputFormat(forBus: 0)
        inputNode.removeTap(onBus: 0)
        inputNode.installTap(onBus: 0, bufferSize: 1024, format: recordingFormat) { buffer, when in
            self.recognitionRequest?.append(buffer)
        }
        
        audioEngine.prepare()
        
        do {
            try audioEngine.start()
        } catch {
            print("audioEngine couldn't start because of an error: \(error.localizedDescription)")
        }
        
        DispatchQueue.main.async {
            self.inputText = ""
        }
    }
    
    private func stopRecordingAndRecognize() {
        audioEngine?.stop()
        recognitionRequest?.endAudio()
        audioEngine?.inputNode.removeTap(onBus: 0)
    }
}

// MARK: - Chat Bubble
struct ChatBubble: View {
    let message: ChatMessage
    
    var body: some View {
        HStack {
            if message.isUser {
                Spacer()
            }
            
            VStack(alignment: message.isUser ? .trailing : .leading, spacing: 4) {
                Text(message.content)
                    .font(.system(size: 15))
                    .foregroundColor(message.isUser ? .white : .primary)
                    .padding(.horizontal, 16)
                    .padding(.vertical, 12)
                    .background(
                        RoundedRectangle(cornerRadius: 20)
                            .fill(message.isUser ? Color("AccentTeal") : Color.white)
                    )
                    .shadow(color: .black.opacity(0.05), radius: 5, y: 2)
                
                if let emoji = message.emoji {
                    Text(emoji)
                        .font(.system(size: 24))
                }
            }
            
            if !message.isUser {
                Spacer()
            }
        }
    }
}

// MARK: - Chat Image Bubble
struct ChatImageBubble: View {
    let message: ChatMessage
    let uiImage: UIImage
    
    var body: some View {
        HStack {
            if message.isUser {
                Spacer()
            }
            
            Image(uiImage: uiImage)
                .resizable()
                .scaledToFit()
                .frame(maxWidth: UIScreen.main.bounds.width * 0.6, maxHeight: 200)
                .clipShape(RoundedRectangle(cornerRadius: 20))
                .background(
                    RoundedRectangle(cornerRadius: 20)
                        .fill(message.isUser ? Color("AccentTeal") : Color.white)
                )
                .shadow(color: .black.opacity(0.05), radius: 5, y: 2)
            
            if !message.isUser {
                Spacer()
            }
        }
    }
}

// MARK: - Typing Indicator
struct TypingIndicator: View {
    @State private var animationOffset: CGFloat = 0
    
    var body: some View {
        HStack {
            HStack(spacing: 4) {
                ForEach(0..<3) { index in
                    Circle()
                        .fill(Color.gray.opacity(0.5))
                        .frame(width: 8, height: 8)
                        .offset(y: animationOffset)
                        .animation(
                            .easeInOut(duration: 0.5)
                            .repeatForever()
                            .delay(Double(index) * 0.15),
                            value: animationOffset
                        )
                }
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 12)
            .background(
                RoundedRectangle(cornerRadius: 20)
                    .fill(Color.white)
            )
            
            Spacer()
        }
        .onAppear {
            animationOffset = -5
        }
    }
}

// MARK: - Character Avatar
struct CharacterAvatar: View {
    let character: CharacterType
    let size: CGFloat
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: size * 0.3)
                .fill(
                    LinearGradient(
                        colors: [Color("AccentTeal").opacity(0.8), Color("AccentTeal")],
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    )
                )
                .frame(width: size, height: size)
            
            // Eyes
            HStack(spacing: size * 0.2) {
                Circle()
                    .fill(Color.cyan)
                    .frame(width: size * 0.2, height: size * 0.2)
                
                Circle()
                    .fill(Color.cyan)
                    .frame(width: size * 0.2, height: size * 0.2)
            }
            .offset(y: -size * 0.05)
            
            // Mouth
            RoundedRectangle(cornerRadius: 2)
                .fill(Color.white.opacity(0.8))
                .frame(width: size * 0.25, height: 4)
                .offset(y: size * 0.2)
        }
    }
}

#if DEBUG
// Provide mock implementations for preview to avoid side effects and missing data

final class PreviewAppState: AppState {
    override init() {
        super.init()
        self.selectedCharacter = .robot
    }
}

final class PreviewChatViewModel: ChatViewModel {
    override init() {
        super.init()
        self.messages = [
            ChatMessage(id: UUID(), content: "嗨，你今天感觉怎么样？", isUser: true, emoji: nil, imageData: nil),
            ChatMessage(id: UUID(), content: "我在这里倾听，也可以给你一些支持。", isUser: false, emoji: "😊", imageData: nil)
        ]
        self.isTyping = false
    }
}

final class PreviewHealthManager: HealthManager {
    override init() {
        super.init()
        // No-op or mock specific health data
    }
}
#endif

#Preview {
    let appState = PreviewAppState()
    let chatViewModel = PreviewChatViewModel()
    let healthManager = PreviewHealthManager()
    ChatView()
        .environmentObject(appState)
        .environmentObject(chatViewModel)
        .environmentObject(healthManager)
}
