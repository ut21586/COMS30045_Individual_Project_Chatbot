//
//  ReportViewModel.swift
//  WithApp
//
//  ViewModel for managing reflective analysis and reports
//

import Foundation
import Combine

// MARK: - Energy Data Point
struct EnergyDataPoint: Identifiable {
    let id: UUID
    let timestamp: Date
    let value: Double  // 0-100 scale
    let label: String
}

// MARK: - Timeline Event
struct TimelineEvent: Identifiable {
    let id: UUID
    let time: String
    let title: String
    let description: String
    let location: String?
    let glucoseValue: Double?
    let mood: String?
    let emoji: String?
    let eventType: EventType
    
    enum EventType {
        case meal
        case activity
        case moodCheckin
        case glucoseEvent
        case general
    }
}

// MARK: - Report View Model
class ReportViewModel: ObservableObject {
    @Published var energyData: [EnergyDataPoint] = []
    @Published var timelineEvents: [TimelineEvent] = []
    @Published var dailyNarrative: String = ""
    @Published var insights: [Insight] = []
    @Published var selectedDate: Date = Date()
    @Published var isLoading = false
    
    private var cancellables = Set<AnyCancellable>()
    
    init() {
        loadMockData()
    }
    
    // MARK: - Load Data
    func loadData(for date: Date) {
        isLoading = true
        selectedDate = date
        
        // Simulate API call
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) { [weak self] in
            self?.loadMockData()
            self?.isLoading = false
        }
    }
    
    private func loadMockData() {
        // Generate energy data for the day
        generateEnergyData()
        
        // Generate timeline events
        generateTimelineEvents()
        
        // Generate daily narrative
        dailyNarrative = generateNarrative()
        
        // Generate insights
        generateInsights()
    }
    
    // MARK: - Energy Data Generation
    private func generateEnergyData() {
        let calendar = Calendar.current
        let now = Date()
        var data: [EnergyDataPoint] = []
        
        // Generate hourly data points
        for hour in 6..<22 {
            if let timestamp = calendar.date(bySettingHour: hour, minute: 0, second: 0, of: now) {
                let baseEnergy = getBaseEnergy(for: hour)
                let variation = Double.random(in: -10...10)
                
                data.append(EnergyDataPoint(
                    id: UUID(),
                    timestamp: timestamp,
                    value: min(100, max(0, baseEnergy + variation)),
                    label: "\(hour):00"
                ))
            }
        }
        
        energyData = data
    }
    
    private func getBaseEnergy(for hour: Int) -> Double {
        switch hour {
        case 6..<8: return 40  // Waking up
        case 8..<10: return 70 // Morning energy
        case 10..<12: return 80 // Peak morning
        case 12..<14: return 55 // Post-lunch dip
        case 14..<17: return 65 // Afternoon
        case 17..<19: return 60 // Evening
        case 19..<22: return 45 // Winding down
        default: return 50
        }
    }
    
    // MARK: - Timeline Generation
    private func generateTimelineEvents() {
        timelineEvents = [
            TimelineEvent(
                id: UUID(),
                time: "7:30",
                title: "清晨",
                description: "以一份清淡的早餐开始新的一天",
                location: nil,
                glucoseValue: 95,
                mood: nil,
                emoji: nil,
                eventType: .meal
            ),
            TimelineEvent(
                id: UUID(),
                time: "9:00",
                title: "晨间散步",
                description: "轻松步行 20 分钟",
                location: "公园",
                glucoseValue: nil,
                mood: "有精神",
                emoji: "🚶‍♂️",
                eventType: .activity
            ),
            TimelineEvent(
                id: UUID(),
                time: "12:30",
                title: "午餐",
                description: "补充能量！你的身体正在把美味的食物转化为能量。",
                location: "绿叶咖啡馆",
                glucoseValue: 8.2,
                mood: "有点饿但不错",
                emoji: "😊",
                eventType: .meal
            ),
            TimelineEvent(
                id: UUID(),
                time: "15:00",
                title: "下午自我检视",
                description: "有点疲惫，但整体还可以",
                location: nil,
                glucoseValue: 120,
                mood: "放松",
                emoji: "😌",
                eventType: .moodCheckin
            ),
            TimelineEvent(
                id: UUID(),
                time: "19:30",
                title: "晚餐",
                description: "烤三文鱼配蔬菜",
                location: "家里",
                glucoseValue: 8.2,
                mood: "放松",
                emoji: nil,
                eventType: .meal
            )
        ]
    }
    
    // MARK: - Narrative Generation
    private func generateNarrative() -> String {
        // In a real app, this would be generated by an LLM based on the day's data
        let narratives = [
            "从清晨忙碌到傍晚宁静。",
            "一天的节律稳稳流动，夹杂着温柔的片刻。",
            "从能量到休息，你的身体找到了平衡。",
            "今天有一些小小的胜利。",
            "经历起伏，最终回归平静。"
        ]
        
        return narratives.randomElement() ?? narratives[0]
    }
    
    // MARK: - Insights Generation
    private func generateInsights() {
        insights = [
            Insight(
                id: UUID(),
                title: "活动影响",
                description: "晨间散步帮助你在午餐前保持了稳定的血糖水平。",
                type: .positive,
                icon: "figure.walk"
            ),
            Insight(
                id: UUID(),
                title: "用餐时间",
                description: "规律的进餐时间支持了你的能量流动。",
                type: .observation,
                icon: "clock"
            ),
            Insight(
                id: UUID(),
                title: "情绪健康",
                description: "你今天进行了两次情绪自我检视，这很棒。",
                type: .encouragement,
                icon: "heart.fill"
            ),
            Insight(
                id: UUID(),
                title: "注意到的模式",
                description: "你的能量常在下午两点左右下降，短暂散步或许有帮助。",
                type: .suggestion,
                icon: "lightbulb.fill"
            )
        ]
    }
    
    // MARK: - Data Analysis
    func getTimeInRange() -> Double {
        // Calculate percentage of time in target glucose range
        // In a real app, this would be calculated from actual data
        return Double.random(in: 65...85)
    }
    
    func getAverageGlucose() -> Double {
        // In a real app, this would be calculated from actual data
        return Double.random(in: 100...130)
    }
    
    func getMoodDistribution() -> [String: Int] {
        // Count of different moods throughout the day
        return [
            "积极": 5,
            "中性": 3,
            "消极": 1
        ]
    }
    
    // MARK: - Reflective Questions
    func getReflectiveQuestion() -> ReflectiveQuestion {
        let questions = [
            ReflectiveQuestion(
                text: "What was on your lunch plate today?",
                type: .meal,
                icon: "fork.knife"
            ),
            ReflectiveQuestion(
                text: "How did your body feel during your morning routine?",
                type: .body,
                icon: "figure.stand"
            ),
            ReflectiveQuestion(
                text: "What moment brought you the most peace today?",
                type: .emotion,
                icon: "heart.fill"
            ),
            ReflectiveQuestion(
                text: "What's one small thing you're grateful for right now?",
                type: .gratitude,
                icon: "sparkles"
            ),
            ReflectiveQuestion(
                text: "How would you describe your energy flow today?",
                type: .energy,
                icon: "bolt.fill"
            )
        ]
        
        return questions.randomElement() ?? questions[0]
    }
}

// MARK: - Insight Model
struct Insight: Identifiable {
    let id: UUID
    let title: String
    let description: String
    let type: InsightType
    let icon: String
    
    enum InsightType {
        case positive
        case observation
        case encouragement
        case suggestion
        case warning
    }
}

// MARK: - Reflective Question Model
struct ReflectiveQuestion: Identifiable {
    let id = UUID()
    let text: String
    let type: QuestionType
    let icon: String
    
    enum QuestionType {
        case meal
        case body
        case emotion
        case gratitude
        case energy
        case activity
    }
}

