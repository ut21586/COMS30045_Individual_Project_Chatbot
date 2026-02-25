//
//  HealthManager.swift
//  WithApp
//
//  Service for managing health data from CGM, HealthKit, and other sources
//

import Foundation
import HealthKit
import Combine

// MARK: - Glucose Level Enum
enum GlucoseLevel {
    case low           // < 70 mg/dL
    case borderlineLow // 70-80 mg/dL
    case normal        // 80-140 mg/dL
    case borderlineHigh // 140-180 mg/dL
    case high          // > 180 mg/dL
    case unknown
    
    static func from(value: Double) -> GlucoseLevel {
        switch value {
        case ..<70:
            return .low
        case 70..<80:
            return .borderlineLow
        case 80..<140:
            return .normal
        case 140..<180:
            return .borderlineHigh
        case 180...:
            return .high
        default:
            return .unknown
        }
    }
    
    var description: String {
        switch self {
        case .low: return "Low"
        case .borderlineLow: return "Borderline Low"
        case .normal: return "Normal"
        case .borderlineHigh: return "Borderline High"
        case .high: return "High"
        case .unknown: return "Unknown"
        }
    }
}

// MARK: - Health Data Models
struct GlucoseReading: Identifiable, Equatable {
    let id: UUID
    let value: Double  // in mg/dL
    let timestamp: Date
    let source: String
    
    var level: GlucoseLevel {
        GlucoseLevel.from(value: value)
    }
    
    var mmolL: Double {
        value / 18.0
    }
}

struct HeartRateReading: Identifiable {
    let id: UUID
    let value: Double  // in BPM
    let timestamp: Date
}

struct ActivityData: Identifiable {
    let id: UUID
    let steps: Int
    let activeCalories: Double
    let distance: Double  // in meters
    let timestamp: Date
}

// MARK: - Health Manager
class HealthManager: ObservableObject {
    private let healthStore = HKHealthStore()
    
    // Published properties
    @Published var isAuthorized = false
    @Published var latestGlucose: GlucoseReading?
    @Published var glucoseHistory: [GlucoseReading] = []
    @Published var latestHeartRate: HeartRateReading?
    @Published var heartRateHistory: [HeartRateReading] = []
    @Published var todayActivity: ActivityData?
    @Published var glucoseLevel: GlucoseLevel = .unknown
    
    // Mock data for demo purposes
    private var useMockData = true
    
    init() {
        if useMockData {
            loadMockData()
        }
    }
    
    // MARK: - Authorization
    func requestAuthorization() {
        guard HKHealthStore.isHealthDataAvailable() else {
            print("HealthKit is not available on this device")
            return
        }
        
        let typesToRead: Set<HKObjectType> = [
            HKObjectType.quantityType(forIdentifier: .bloodGlucose)!,
            HKObjectType.quantityType(forIdentifier: .heartRate)!,
            HKObjectType.quantityType(forIdentifier: .stepCount)!,
            HKObjectType.quantityType(forIdentifier: .activeEnergyBurned)!,
            HKObjectType.quantityType(forIdentifier: .distanceWalkingRunning)!
        ]
        
        healthStore.requestAuthorization(toShare: nil, read: typesToRead) { [weak self] success, error in
            DispatchQueue.main.async {
                self?.isAuthorized = success
                if success {
                    self?.fetchAllData()
                }
            }
        }
    }
    
    // MARK: - Data Fetching
    func fetchAllData() {
        fetchGlucoseData()
        fetchHeartRateData()
        fetchActivityData()
    }
    
    private func fetchGlucoseData() {
        guard let glucoseType = HKObjectType.quantityType(forIdentifier: .bloodGlucose) else { return }
        
        let sortDescriptor = NSSortDescriptor(key: HKSampleSortIdentifierStartDate, ascending: false)
        let query = HKSampleQuery(
            sampleType: glucoseType,
            predicate: nil,
            limit: 100,
            sortDescriptors: [sortDescriptor]
        ) { [weak self] _, samples, error in
            guard let samples = samples as? [HKQuantitySample] else { return }
            
            DispatchQueue.main.async {
                self?.glucoseHistory = samples.map { sample in
                    GlucoseReading(
                        id: UUID(),
                        value: sample.quantity.doubleValue(for: HKUnit(from: "mg/dL")),
                        timestamp: sample.startDate,
                        source: sample.sourceRevision.source.name
                    )
                }
                self?.latestGlucose = self?.glucoseHistory.first
                self?.glucoseLevel = self?.latestGlucose?.level ?? .unknown
            }
        }
        
        healthStore.execute(query)
    }
    
    private func fetchHeartRateData() {
        guard let heartRateType = HKObjectType.quantityType(forIdentifier: .heartRate) else { return }
        
        let sortDescriptor = NSSortDescriptor(key: HKSampleSortIdentifierStartDate, ascending: false)
        let query = HKSampleQuery(
            sampleType: heartRateType,
            predicate: nil,
            limit: 50,
            sortDescriptors: [sortDescriptor]
        ) { [weak self] _, samples, error in
            guard let samples = samples as? [HKQuantitySample] else { return }
            
            DispatchQueue.main.async {
                self?.heartRateHistory = samples.map { sample in
                    HeartRateReading(
                        id: UUID(),
                        value: sample.quantity.doubleValue(for: HKUnit(from: "count/min")),
                        timestamp: sample.startDate
                    )
                }
                self?.latestHeartRate = self?.heartRateHistory.first
            }
        }
        
        healthStore.execute(query)
    }
    
    private func fetchActivityData() {
        let calendar = Calendar.current
        let now = Date()
        let startOfDay = calendar.startOfDay(for: now)
        let predicate = HKQuery.predicateForSamples(withStart: startOfDay, end: now)
        
        // Fetch steps
        guard let stepsType = HKQuantityType.quantityType(forIdentifier: .stepCount) else { return }
        
        let stepsQuery = HKStatisticsQuery(
            quantityType: stepsType,
            quantitySamplePredicate: predicate,
            options: .cumulativeSum
        ) { [weak self] _, result, error in
            guard let result = result, let sum = result.sumQuantity() else { return }
            
            let steps = Int(sum.doubleValue(for: HKUnit.count()))
            
            DispatchQueue.main.async {
                self?.todayActivity = ActivityData(
                    id: UUID(),
                    steps: steps,
                    activeCalories: 0,
                    distance: 0,
                    timestamp: now
                )
            }
        }
        
        healthStore.execute(stepsQuery)
    }
    
    // MARK: - Mock Data
    private func loadMockData() {
        // Generate mock glucose readings
        let now = Date()
        var mockReadings: [GlucoseReading] = []
        
        for i in 0..<24 {
            let timestamp = Calendar.current.date(byAdding: .hour, value: -i, to: now)!
            let baseValue = 105.0
            let variation = Double.random(in: -25...35)
            
            mockReadings.append(GlucoseReading(
                id: UUID(),
                value: baseValue + variation,
                timestamp: timestamp,
                source: "Dexcom G7"
            ))
        }
        
        glucoseHistory = mockReadings
        latestGlucose = mockReadings.first
        glucoseLevel = latestGlucose?.level ?? .normal
        
        // Mock heart rate
        latestHeartRate = HeartRateReading(
            id: UUID(),
            value: Double.random(in: 65...85),
            timestamp: now
        )
        
        // Mock activity
        todayActivity = ActivityData(
            id: UUID(),
            steps: Int.random(in: 3000...8000),
            activeCalories: Double.random(in: 150...400),
            distance: Double.random(in: 2000...6000),
            timestamp: now
        )
    }
    
    // MARK: - CGM Connection Simulation
    func connectToCGM(deviceId: String, completion: @escaping (Bool) -> Void) {
        // Simulate connection delay
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            // Simulate successful connection
            completion(true)
        }
    }
    
    func disconnectCGM() {
        // Clear real-time data
    }
    
    // MARK: - Data Analysis
    func getGlucoseTrend() -> GlucoseTrend {
        guard glucoseHistory.count >= 3 else { return .stable }
        
        let recent = glucoseHistory.prefix(3).map { $0.value }
        let average = recent.reduce(0, +) / Double(recent.count)
        let first = recent.first ?? 0
        
        if first - average > 10 {
            return .rising
        } else if average - first > 10 {
            return .falling
        }
        return .stable
    }
    
    func getTodaySummary() -> DaySummary {
        let readings = glucoseHistory.filter { 
            Calendar.current.isDateInToday($0.timestamp)
        }
        
        let values = readings.map { $0.value }
        let average = values.isEmpty ? 0 : values.reduce(0, +) / Double(values.count)
        let inRange = values.filter { $0 >= 70 && $0 <= 180 }.count
        let timeInRange = values.isEmpty ? 0 : Double(inRange) / Double(values.count) * 100
        
        return DaySummary(
            averageGlucose: average,
            timeInRange: timeInRange,
            readingsCount: readings.count,
            steps: todayActivity?.steps ?? 0
        )
    }
}

// MARK: - Supporting Types
enum GlucoseTrend {
    case rising
    case falling
    case stable
    
    var icon: String {
        switch self {
        case .rising: return "arrow.up.right"
        case .falling: return "arrow.down.right"
        case .stable: return "arrow.right"
        }
    }
}

struct DaySummary {
    let averageGlucose: Double
    let timeInRange: Double
    let readingsCount: Int
    let steps: Int
}
