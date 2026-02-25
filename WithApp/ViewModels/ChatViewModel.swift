//
//  ChatViewModel.swift
//  WithApp
//
//  ViewModel for managing chat conversations with LLM integration
//

import Foundation
import Combine

// MARK: - Chat Message Model
struct ChatMessage: Identifiable, Codable {
    let id: UUID
    let content: String
    let isUser: Bool
    let timestamp: Date
    let emoji: String?
    let glucoseContext: Double?
    let activityContext: String?
    let imageData: Data?
    
    init(
        id: UUID = UUID(),
        content: String,
        isUser: Bool,
        timestamp: Date = Date(),
        emoji: String? = nil,
        glucoseContext: Double? = nil,
        activityContext: String? = nil,
        imageData: Data? = nil
    ) {
        self.id = id
        self.content = content
        self.isUser = isUser
        self.timestamp = timestamp
        self.emoji = emoji
        self.glucoseContext = glucoseContext
        self.activityContext = activityContext
        self.imageData = imageData
    }
}

// MARK: - Chat View Model
class ChatViewModel: ObservableObject {
    @Published var messages: [ChatMessage] = []
    @Published var isTyping = false
    @Published var conversationContext: ConversationContext = .general
    
    private var cancellables = Set<AnyCancellable>()
    private let llmService = LLMService()
    
    // Quick input suggestions based on user patterns
    @Published var quickSuggestions: [String] = [
        "我也说不清为什么难过。",
        "感觉压力很大。",
        "今天过得有点艰难。",
        "其实我还可以。",
        "想找个人聊聊。"
    ]
    
    init() {
        // Load initial welcome message
        loadInitialMessage()
    }
    
    private func loadInitialMessage() {
        let welcomeMessage = ChatMessage(
            content: "嗨，我在这里陪着你。你今天感觉怎么样？",
            isUser: false
        )
        messages.append(welcomeMessage)
    }
    
    // MARK: - Send Message
    func sendMessage(_ text: String, emoji: String? = nil) {
        let trimmedText = text.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !trimmedText.isEmpty else { return }
        
        // Add user message
        let userMessage = ChatMessage(
            content: trimmedText,
            isUser: true,
            emoji: emoji
        )
        messages.append(userMessage)
        
        // Update suggestions based on user input
        updateSuggestions(basedOn: trimmedText)
        
        // Generate response
        generateResponse(to: trimmedText)
    }
    
    // MARK: - Send Image Message
    func sendImageMessage(_ imageData: Data) {
        let imageMessage = ChatMessage(
            content: "",
            isUser: true,
            imageData: imageData
        )
        messages.append(imageMessage)
    }
    
    // MARK: - Generate Response
    private func generateResponse(to userInput: String) {
        isTyping = true
        
        // Analyze emotion from input
        let emotion = analyzeEmotion(from: userInput)
        
        // Simulate LLM response delay
        DispatchQueue.main.asyncAfter(deadline: .now() + Double.random(in: 1.0...2.5)) { [weak self] in
            guard let self = self else { return }
            
            let response = self.llmService.generateSupportiveResponse(
                userInput: userInput,
                emotion: emotion,
                context: self.conversationContext
            )
            
            // Add response messages with slight delays for natural feel
            self.addResponseMessages(response.messages)
        }
    }
    
    private func addResponseMessages(_ responseTexts: [String]) {
        var delay: Double = 0
        
        for text in responseTexts {
            DispatchQueue.main.asyncAfter(deadline: .now() + delay) { [weak self] in
                let message = ChatMessage(
                    content: text,
                    isUser: false
                )
                self?.messages.append(message)
                
                // Stop typing indicator after last message
                if text == responseTexts.last {
                    self?.isTyping = false
                }
            }
            delay += Double.random(in: 0.8...1.5)
        }
    }
    
    // MARK: - Emotion Analysis
    private func analyzeEmotion(from text: String) -> EmotionType {
        let lowercased = text.lowercased()
        
        // Simple keyword-based emotion detection
        let negativeKeywords = ["难过","伤心","生气","沮丧","不堪重负","压力大","焦虑","担心","害怕","困难","辛苦","累","精疲力竭","upset","sad","angry","frustrated","overwhelmed","stressed","anxious","worried","scared","difficult","hard","tired","exhausted"]
        let positiveKeywords = ["开心","高兴","很好","不错","还行","更好","平静","放松","安宁","happy","good","great","fine","okay","better","calm","relaxed","peaceful"]
        let uncertainKeywords = ["不知道","不确定","困惑","也许","not sure","don't know","confused","unsure","maybe"]
        
        if negativeKeywords.contains(where: { lowercased.contains($0) }) {
            return .negative
        } else if positiveKeywords.contains(where: { lowercased.contains($0) }) {
            return .positive
        } else if uncertainKeywords.contains(where: { lowercased.contains($0) }) {
            return .uncertain
        }
        
        return .neutral
    }
    
    // MARK: - Update Suggestions
    private func updateSuggestions(basedOn input: String) {
        // In a real app, this would use ML to generate personalized suggestions
        let emotion = analyzeEmotion(from: input)
        
        switch emotion {
        case .negative:
            quickSuggestions = [
                "I feel a bit better now",
                "Can you help me understand this feeling?",
                "I need to take a moment",
                "Tell me something positive",
                "I want to try a breathing exercise"
            ]
        case .positive:
            quickSuggestions = [
                "I want to share more",
                "What should I focus on today?",
                "How can I maintain this feeling?",
                "Let's set a small goal",
                "Thank you for being here"
            ]
        default:
            quickSuggestions = [
                "I'm not sure how I feel",
                "Can we talk about something else?",
                "I'd like some guidance",
                "What do you suggest?",
                "Let me think about it"
            ]
        }
    }
    
    // MARK: - Clear Chat
    func clearChat() {
        messages.removeAll()
        loadInitialMessage()
    }
}

// MARK: - Emotion Type
enum EmotionType {
    case positive
    case negative
    case neutral
    case uncertain
}

// MARK: - Conversation Context
enum ConversationContext {
    case general
    case mealTime
    case exercise
    case sleep
    case stress
    case medical
}

// MARK: - LLM Service
class LLMService {
    // Supportive response templates based on emotion and context
    private let supportiveResponses: [EmotionType: [[String]]] = [
        .negative: [
            [
                "当这种感觉出现时，往往是很多事情一起叠加的结果。",
                "你的身体、你的一天，还有你心里背负的东西，都可能在其中。",
                "或许我们可以把今天当成一个整体来看，而不是只盯着某个数字或片刻？"
            ],
            [
                "没关系，你不一定需要一个清晰的理由。",
                "有时候身体先感受到，语言会慢一点跟上。",
                "如果温柔地觉察一下，你现在最明显的感觉在哪里？"
            ],
            [
                "我听到了，确实不容易。",
                "你不需要现在就把一切想明白。",
                "此刻愿意说出来并承认它，本身就是向前的一步。"
            ]
        ],
        .positive: [
            [
                "太好了，听到你这样说我很开心！",
                "你能觉察到这些美好的时刻，真的很棒。",
                "你觉得今天有哪些因素促成了这样的感觉？"
            ],
            [
                "真为你感到高兴。",
                "这些时刻很重要。要不要记录一下是什么帮到了你？",
                "别忘了庆祝这些小小的进步。"
            ]
        ],
        .neutral: [
            [
                "谢谢你愿意分享。",
                "到目前为止，你今天过得怎么样？",
                "有没有你想特别聊聊的事情？"
            ],
            [
                "我在这儿，随时愿意倾听。",
                "不用着急，慢慢来就好。",
                "你现在最在意的是什么？"
            ]
        ],
        .uncertain: [
            [
                "不知道答案也完全没关系。",
                "有时候我们的感受是很复杂的。",
                "愿意和我一起慢慢看看吗？"
            ],
            [
                "不确定也是一种真实的感受。",
                "我们可以一步一步来。",
                "此刻对你来说，最突出的感受是什么？"
            ]
        ]
    ]
    
    struct LLMResponse {
        let messages: [String]
        let suggestedActions: [String]
    }
    
    func generateSupportiveResponse(
        userInput: String,
        emotion: EmotionType,
        context: ConversationContext
    ) -> LLMResponse {
        // In a real implementation, this would call an LLM API
        // For now, we use pre-defined supportive responses
        
        let responses = supportiveResponses[emotion] ?? supportiveResponses[.neutral]!
        let selectedResponse = responses.randomElement() ?? responses[0]
        
        return LLMResponse(
            messages: selectedResponse,
            suggestedActions: generateSuggestedActions(for: emotion, context: context)
        )
    }
    
    private func generateSuggestedActions(
        for emotion: EmotionType,
        context: ConversationContext
    ) -> [String] {
        switch emotion {
        case .negative:
            return [
                "做个呼吸练习",
                "写一段心情日记",
                "听些舒缓的音乐",
                "出去走一小会儿"
            ]
        case .positive:
            return [
                "把这个时刻记进你的故事",
                "与重要的人分享",
                "设定一个积极的小目标"
            ]
        default:
            return [
                "继续聊聊",
                "查看今日报告",
                "回顾一下你的进展"
            ]
        }
    }
}

