//
//  CharacterView.swift
//  WithApp
//
//  Animated character display component
//

import SwiftUI

struct CharacterView: View {
    let character: CharacterType
    @Binding var isAnimating: Bool
    
    @State private var eyeOffset: CGFloat = 0
    @State private var bounceOffset: CGFloat = 0
    @State private var blinkOpacity: Double = 1.0
    
    var body: some View {
        ZStack {
            // Shadow
            Ellipse()
                .fill(Color.black.opacity(0.1))
                .frame(width: 100, height: 30)
                .offset(y: 70 + bounceOffset * 0.5)
                .blur(radius: 5)
            
            // Character body
            characterBody
                .offset(y: bounceOffset)
        }
        .onAppear {
            startAnimations()
        }
    }
    
    @ViewBuilder
    private var characterBody: some View {
        switch character {
        case .robot:
            RobotCharacter(eyeOffset: eyeOffset, blinkOpacity: blinkOpacity)
        case .cat:
            CatCharacter(eyeOffset: eyeOffset, blinkOpacity: blinkOpacity)
        case .bear:
            BearCharacter(eyeOffset: eyeOffset, blinkOpacity: blinkOpacity)
        case .bunny:
            BunnyCharacter(eyeOffset: eyeOffset, blinkOpacity: blinkOpacity)
        case .panda:
            PandaCharacter(eyeOffset: eyeOffset, blinkOpacity: blinkOpacity)
        }
    }
    
    private func startAnimations() {
        // Bounce animation
        withAnimation(.easeInOut(duration: 2).repeatForever(autoreverses: true)) {
            bounceOffset = -10
        }
        
        // Eye movement
        Timer.scheduledTimer(withTimeInterval: 3, repeats: true) { _ in
            withAnimation(.easeInOut(duration: 0.3)) {
                eyeOffset = CGFloat.random(in: -3...3)
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                withAnimation(.easeInOut(duration: 0.3)) {
                    eyeOffset = 0
                }
            }
        }
        
        // Blink animation
        Timer.scheduledTimer(withTimeInterval: 4, repeats: true) { _ in
            withAnimation(.easeInOut(duration: 0.1)) {
                blinkOpacity = 0.1
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) {
                withAnimation(.easeInOut(duration: 0.1)) {
                    blinkOpacity = 1.0
                }
            }
        }
    }
}

// MARK: - Robot Character
struct RobotCharacter: View {
    let eyeOffset: CGFloat
    let blinkOpacity: Double
    
    var body: some View {
        ZStack {
            // Body
            RoundedRectangle(cornerRadius: 30)
                .fill(
                    LinearGradient(
                        colors: [Color(red: 0.2, green: 0.5, blue: 0.5), Color(red: 0.15, green: 0.4, blue: 0.45)],
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    )
                )
                .frame(width: 120, height: 120)
                .shadow(color: Color("AccentTeal").opacity(0.3), radius: 20)
            
            // Face container
            RoundedRectangle(cornerRadius: 20)
                .fill(Color(red: 0.2, green: 0.45, blue: 0.5))
                .frame(width: 100, height: 80)
                .offset(y: -5)
            
            // Eyes
            HStack(spacing: 25) {
                // Left eye
                Circle()
                    .fill(Color.cyan)
                    .frame(width: 22, height: 22 * blinkOpacity)
                    .shadow(color: .cyan.opacity(0.8), radius: 8)
                    .offset(x: eyeOffset)
                
                // Right eye
                Circle()
                    .fill(Color.cyan)
                    .frame(width: 22, height: 22 * blinkOpacity)
                    .shadow(color: .cyan.opacity(0.8), radius: 8)
                    .offset(x: eyeOffset)
            }
            .offset(y: -10)
            
            // Mouth
            RoundedRectangle(cornerRadius: 3)
                .fill(Color.white.opacity(0.7))
                .frame(width: 30, height: 6)
                .offset(y: 25)
            
            // Antenna
            VStack(spacing: 0) {
                Circle()
                    .fill(Color.cyan)
                    .frame(width: 10, height: 10)
                    .shadow(color: .cyan, radius: 5)
                
                Rectangle()
                    .fill(Color.gray)
                    .frame(width: 3, height: 15)
            }
            .offset(y: -75)
        }
    }
}

// MARK: - Cat Character
struct CatCharacter: View {
    let eyeOffset: CGFloat
    let blinkOpacity: Double
    
    var body: some View {
        ZStack {
            // Body
            Circle()
                .fill(
                    LinearGradient(
                        colors: [Color.orange.opacity(0.9), Color.orange.opacity(0.7)],
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    )
                )
                .frame(width: 120, height: 120)
            
            // Ears
            HStack(spacing: 70) {
                Triangle()
                    .fill(Color.orange.opacity(0.9))
                    .frame(width: 35, height: 35)
                    .rotationEffect(.degrees(-15))
                
                Triangle()
                    .fill(Color.orange.opacity(0.9))
                    .frame(width: 35, height: 35)
                    .rotationEffect(.degrees(15))
            }
            .offset(y: -55)
            
            // Inner ears
            HStack(spacing: 75) {
                Triangle()
                    .fill(Color.pink.opacity(0.5))
                    .frame(width: 20, height: 20)
                    .rotationEffect(.degrees(-15))
                
                Triangle()
                    .fill(Color.pink.opacity(0.5))
                    .frame(width: 20, height: 20)
                    .rotationEffect(.degrees(15))
            }
            .offset(y: -50)
            
            // Eyes
            HStack(spacing: 30) {
                Circle()
                    .fill(Color.black)
                    .frame(width: 16, height: 16 * blinkOpacity)
                    .offset(x: eyeOffset)
                
                Circle()
                    .fill(Color.black)
                    .frame(width: 16, height: 16 * blinkOpacity)
                    .offset(x: eyeOffset)
            }
            .offset(y: -5)
            
            // Nose
            Triangle()
                .fill(Color.pink)
                .frame(width: 12, height: 10)
                .rotationEffect(.degrees(180))
                .offset(y: 15)
            
            // Whiskers
            HStack(spacing: 50) {
                VStack(spacing: 8) {
                    Rectangle().fill(Color.gray.opacity(0.5)).frame(width: 25, height: 2).rotationEffect(.degrees(-10))
                    Rectangle().fill(Color.gray.opacity(0.5)).frame(width: 25, height: 2)
                    Rectangle().fill(Color.gray.opacity(0.5)).frame(width: 25, height: 2).rotationEffect(.degrees(10))
                }
                .offset(x: -15)
                
                VStack(spacing: 8) {
                    Rectangle().fill(Color.gray.opacity(0.5)).frame(width: 25, height: 2).rotationEffect(.degrees(10))
                    Rectangle().fill(Color.gray.opacity(0.5)).frame(width: 25, height: 2)
                    Rectangle().fill(Color.gray.opacity(0.5)).frame(width: 25, height: 2).rotationEffect(.degrees(-10))
                }
                .offset(x: 15)
            }
            .offset(y: 20)
        }
    }
}

// MARK: - Bear Character
struct BearCharacter: View {
    let eyeOffset: CGFloat
    let blinkOpacity: Double
    
    var body: some View {
        ZStack {
            // Ears
            HStack(spacing: 80) {
                Circle()
                    .fill(Color.brown)
                    .frame(width: 40, height: 40)
                
                Circle()
                    .fill(Color.brown)
                    .frame(width: 40, height: 40)
            }
            .offset(y: -50)
            
            // Inner ears
            HStack(spacing: 85) {
                Circle()
                    .fill(Color.brown.opacity(0.6))
                    .frame(width: 25, height: 25)
                
                Circle()
                    .fill(Color.brown.opacity(0.6))
                    .frame(width: 25, height: 25)
            }
            .offset(y: -50)
            
            // Body
            Circle()
                .fill(
                    LinearGradient(
                        colors: [Color.brown, Color.brown.opacity(0.8)],
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    )
                )
                .frame(width: 120, height: 120)
            
            // Muzzle
            Ellipse()
                .fill(Color.brown.opacity(0.6))
                .frame(width: 60, height: 45)
                .offset(y: 15)
            
            // Eyes
            HStack(spacing: 35) {
                Circle()
                    .fill(Color.black)
                    .frame(width: 14, height: 14 * blinkOpacity)
                    .offset(x: eyeOffset)
                
                Circle()
                    .fill(Color.black)
                    .frame(width: 14, height: 14 * blinkOpacity)
                    .offset(x: eyeOffset)
            }
            .offset(y: -10)
            
            // Nose
            Ellipse()
                .fill(Color.black)
                .frame(width: 18, height: 12)
                .offset(y: 10)
            
            // Smile
            Path { path in
                path.move(to: CGPoint(x: -10, y: 0))
                path.addQuadCurve(
                    to: CGPoint(x: 10, y: 0),
                    control: CGPoint(x: 0, y: 8)
                )
            }
            .stroke(Color.black, lineWidth: 2)
            .frame(width: 20, height: 10)
            .offset(y: 25)
        }
    }
}

// MARK: - Bunny Character
struct BunnyCharacter: View {
    let eyeOffset: CGFloat
    let blinkOpacity: Double
    
    var body: some View {
        ZStack {
            // Ears
            HStack(spacing: 30) {
                Capsule()
                    .fill(Color.white)
                    .frame(width: 30, height: 70)
                    .rotationEffect(.degrees(-10))
                    .overlay(
                        Capsule()
                            .fill(Color.pink.opacity(0.4))
                            .frame(width: 15, height: 50)
                    )
                
                Capsule()
                    .fill(Color.white)
                    .frame(width: 30, height: 70)
                    .rotationEffect(.degrees(10))
                    .overlay(
                        Capsule()
                            .fill(Color.pink.opacity(0.4))
                            .frame(width: 15, height: 50)
                    )
            }
            .offset(y: -80)
            
            // Body
            Circle()
                .fill(Color.white)
                .frame(width: 120, height: 120)
                .shadow(color: .gray.opacity(0.2), radius: 10)
            
            // Cheeks
            HStack(spacing: 60) {
                Circle()
                    .fill(Color.pink.opacity(0.3))
                    .frame(width: 25, height: 25)
                
                Circle()
                    .fill(Color.pink.opacity(0.3))
                    .frame(width: 25, height: 25)
            }
            .offset(y: 10)
            
            // Eyes
            HStack(spacing: 30) {
                Circle()
                    .fill(Color.black)
                    .frame(width: 12, height: 12 * blinkOpacity)
                    .offset(x: eyeOffset)
                
                Circle()
                    .fill(Color.black)
                    .frame(width: 12, height: 12 * blinkOpacity)
                    .offset(x: eyeOffset)
            }
            .offset(y: -5)
            
            // Nose
            Ellipse()
                .fill(Color.pink)
                .frame(width: 12, height: 8)
                .offset(y: 15)
            
            // Mouth
            Text("ω")
                .font(.system(size: 20))
                .foregroundColor(.gray)
                .offset(y: 25)
        }
    }
}

// MARK: - Panda Character
struct PandaCharacter: View {
    let eyeOffset: CGFloat
    let blinkOpacity: Double
    
    var body: some View {
        ZStack {
            // Ears
            HStack(spacing: 80) {
                Circle()
                    .fill(Color.black)
                    .frame(width: 35, height: 35)
                
                Circle()
                    .fill(Color.black)
                    .frame(width: 35, height: 35)
            }
            .offset(y: -50)
            
            // Body
            Circle()
                .fill(Color.white)
                .frame(width: 120, height: 120)
            
            // Eye patches
            HStack(spacing: 25) {
                Ellipse()
                    .fill(Color.black)
                    .frame(width: 35, height: 30)
                    .rotationEffect(.degrees(-15))
                
                Ellipse()
                    .fill(Color.black)
                    .frame(width: 35, height: 30)
                    .rotationEffect(.degrees(15))
            }
            .offset(y: -5)
            
            // Eyes
            HStack(spacing: 35) {
                Circle()
                    .fill(Color.white)
                    .frame(width: 12, height: 12 * blinkOpacity)
                    .offset(x: eyeOffset)
                
                Circle()
                    .fill(Color.white)
                    .frame(width: 12, height: 12 * blinkOpacity)
                    .offset(x: eyeOffset)
            }
            .offset(y: -5)
            
            // Nose
            Ellipse()
                .fill(Color.black)
                .frame(width: 15, height: 10)
                .offset(y: 15)
            
            // Smile
            Path { path in
                path.move(to: CGPoint(x: -8, y: 0))
                path.addQuadCurve(
                    to: CGPoint(x: 8, y: 0),
                    control: CGPoint(x: 0, y: 6)
                )
            }
            .stroke(Color.black, lineWidth: 2)
            .frame(width: 16, height: 8)
            .offset(y: 28)
        }
    }
}

// MARK: - Triangle Shape
struct Triangle: Shape {
    func path(in rect: CGRect) -> Path {
        var path = Path()
        path.move(to: CGPoint(x: rect.midX, y: rect.minY))
        path.addLine(to: CGPoint(x: rect.maxX, y: rect.maxY))
        path.addLine(to: CGPoint(x: rect.minX, y: rect.maxY))
        path.closeSubpath()
        return path
    }
}

#Preview {
    VStack(spacing: 40) {
        CharacterView(character: .robot, isAnimating: .constant(true))
        CharacterView(character: .cat, isAnimating: .constant(true))
    }
}
