//
//  SupportMessageBubble.swift
//  WithApp
//
//  Component for displaying supportive messages based on glucose levels
//

import SwiftUI

struct SupportMessageBubble: View {
    let message: SupportMessage
    @State private var isVisible = false
    
    var body: some View {
        VStack(spacing: 8) {
            Text(message.text)
                .font(.system(size: 15))
                .foregroundColor(.primary)
                .multilineTextAlignment(.center)
                .lineSpacing(4)
            
            Text("双击或上滑继续")
                .font(.system(size: 11))
                .foregroundColor(Color("AccentTeal").opacity(0.7))
        }
        .padding(.horizontal, 24)
        .padding(.vertical, 16)
        .background(
            MessageBubbleShape()
                .fill(Color.white)
                .shadow(color: .black.opacity(0.08), radius: 15, y: 5)
        )
        .opacity(isVisible ? 1 : 0)
        .offset(y: isVisible ? 0 : 10)
        .onAppear {
            withAnimation(.easeOut(duration: 0.5)) {
                isVisible = true
            }
        }
    }
}

// MARK: - Message Bubble Shape
struct MessageBubbleShape: Shape {
    func path(in rect: CGRect) -> Path {
        var path = Path()
        let cornerRadius: CGFloat = 20
        let arrowSize: CGFloat = 12
        let arrowX = rect.midX
        
        // Start from top left
        path.move(to: CGPoint(x: rect.minX + cornerRadius, y: rect.minY))
        
        // Top edge
        path.addLine(to: CGPoint(x: rect.maxX - cornerRadius, y: rect.minY))
        
        // Top right corner
        path.addQuadCurve(
            to: CGPoint(x: rect.maxX, y: rect.minY + cornerRadius),
            control: CGPoint(x: rect.maxX, y: rect.minY)
        )
        
        // Right edge
        path.addLine(to: CGPoint(x: rect.maxX, y: rect.maxY - cornerRadius))
        
        // Bottom right corner
        path.addQuadCurve(
            to: CGPoint(x: rect.maxX - cornerRadius, y: rect.maxY),
            control: CGPoint(x: rect.maxX, y: rect.maxY)
        )
        
        // Bottom edge with arrow
        path.addLine(to: CGPoint(x: arrowX + arrowSize, y: rect.maxY))
        path.addLine(to: CGPoint(x: arrowX, y: rect.maxY + arrowSize))
        path.addLine(to: CGPoint(x: arrowX - arrowSize, y: rect.maxY))
        path.addLine(to: CGPoint(x: rect.minX + cornerRadius, y: rect.maxY))
        
        // Bottom left corner
        path.addQuadCurve(
            to: CGPoint(x: rect.minX, y: rect.maxY - cornerRadius),
            control: CGPoint(x: rect.minX, y: rect.maxY)
        )
        
        // Left edge
        path.addLine(to: CGPoint(x: rect.minX, y: rect.minY + cornerRadius))
        
        // Top left corner
        path.addQuadCurve(
            to: CGPoint(x: rect.minX + cornerRadius, y: rect.minY),
            control: CGPoint(x: rect.minX, y: rect.minY)
        )
        
        return path
    }
}

// MARK: - Support Message Model
struct SupportMessage {
    let text: String
    let level: SupportLevel
    
    enum SupportLevel {
        case stable      // Gentle encouragement
        case borderline  // Soft reassurance
        case concerning  // Supportive prompt
    }
    
    static let defaultMessage = SupportMessage(
        text: "你的血糖在理想范围内。你现在感觉怎么样？",
        level: .stable
    )
    
    static func getMessage(for glucoseLevel: GlucoseLevel, context: ContextMode) -> SupportMessage {
        switch glucoseLevel {
        case .low:
            return SupportMessage(
                text: concerningMessages.randomElement() ?? defaultMessage.text,
                level: .concerning
            )
        case .borderlineLow, .borderlineHigh:
            return SupportMessage(
                text: borderlineMessages.randomElement() ?? defaultMessage.text,
                level: .borderline
            )
        case .normal:
            return contextAwareMessage(for: context)
        case .high:
            return SupportMessage(
                text: concerningMessages.randomElement() ?? defaultMessage.text,
                level: .concerning
            )
        case .unknown:
            return defaultMessage
        }
    }
    
    private static func contextAwareMessage(for context: ContextMode) -> SupportMessage {
        switch context {
        case .restaurant:
            return SupportMessage(
                text: "你现在像是在就餐场景。吃东西前，想先聊聊此刻的感受吗？",
                level: .stable
            )
        case .work:
            return SupportMessage(
                text: "工作间隙也值得给自己一点时间。你的状态很重要。",
                level: .stable
            )
        case .social:
            return SupportMessage(
                text: "社交时刻会有开心，也可能有压力。我在这里陪你。",
                level: .stable
            )
        default:
            return SupportMessage(
                text: stableMessages.randomElement() ?? defaultMessage.text,
                level: .stable
            )
        }
    }
    
    // MARK: - Message Collections
    private static let stableMessages = [
        "你的血糖在理想范围内。你现在感觉怎么样？",
        "今天整体比较平稳。想回顾一下今天的状态吗？",
        "身体状态不错，也想关心一下你的心情。",
        "给自己一个温柔的停顿。你今天的精力怎么样？",
        "数字很平稳。现在有没有什么在你心里？"
    ]
    
    private static let borderlineMessages = [
        "我注意到一些波动，不用紧张，我只是来关心你。",
        "身体正在调整。你此刻感觉如何？",
        "有波动是很常见的。想和我聊聊吗？",
        "温柔提醒一下：你已经很努力了。我可以怎么支持你？",
        "不管起伏如何，我都会陪着你。"
    ]
    
    private static let concerningMessages = [
        "我注意到一个需要留意的信号。你现在感觉怎么样？",
        "我们先一起慢下来。我会在这里支持你。",
        "你的整体状态最重要。愿意说说你现在的情况吗？",
        "我在这儿陪你。我们一起看看你此刻的感受。",
        "你不是一个人在面对这些。我可以怎么帮你？"
    ]
}

// MARK: - Preview
#Preview {
    VStack(spacing: 30) {
        SupportMessageBubble(message: .defaultMessage)
        
        SupportMessageBubble(message: SupportMessage(
            text: "我注意到一些波动，不用紧张，我只是来关心你。",
            level: .borderline
        ))
    }
    .padding()
    .background(Color(red: 0.95, green: 0.98, blue: 0.97))
}
