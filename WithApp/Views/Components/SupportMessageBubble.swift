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
            
            Text("Double tap or swipe")
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
        text: "\"Your blood glucose seems to be in a good range. How do you feel about it?\"",
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
                text: "\"You seem to be at a restaurant. Do you need emotional support before eating?\"",
                level: .stable
            )
        case .work:
            return SupportMessage(
                text: "\"Taking a moment for yourself during work. Your wellbeing matters.\"",
                level: .stable
            )
        case .social:
            return SupportMessage(
                text: "\"Social moments can be both fun and challenging. I'm here if you need me.\"",
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
        "\"Your blood glucose seems to be in a good range. How do you feel about it?\"",
        "\"Things look steady right now. Would you like to reflect on your day?\"",
        "\"Your body is doing great. How are you feeling emotionally?\"",
        "\"A gentle moment to check in. How's your energy today?\"",
        "\"Your numbers look calm. Is there anything on your mind?\""
    ]
    
    private static let borderlineMessages = [
        "\"I notice some changes. There's no need to worry—just checking in.\"",
        "\"Your body is adjusting. How are you feeling right now?\"",
        "\"Sometimes things fluctuate. Would you like to talk about it?\"",
        "\"A gentle reminder: you're doing your best. How can I support you?\"",
        "\"I'm here with you through the ups and downs.\""
    ]
    
    private static let concerningMessages = [
        "\"I'm noticing something that might need attention. How are you feeling?\"",
        "\"Let's take a moment together. I'm here to support you.\"",
        "\"Your wellbeing matters most. Would you like to share how you're doing?\"",
        "\"I'm here for you. Let's check in on how you're feeling.\"",
        "\"Remember, you're not alone in this. How can I help?\""
    ]
}

// MARK: - Preview
#Preview {
    VStack(spacing: 30) {
        SupportMessageBubble(message: .defaultMessage)
        
        SupportMessageBubble(message: SupportMessage(
            text: "\"I notice some changes. There's no need to worry—just checking in.\"",
            level: .borderline
        ))
    }
    .padding()
    .background(Color(red: 0.95, green: 0.98, blue: 0.97))
}
