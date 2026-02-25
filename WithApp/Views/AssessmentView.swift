import SwiftUI

struct AssessmentView: View {
    @Environment(\.dismiss) var dismiss
    @State private var q1: Double = 5
    @State private var q2: String = ""
    @State private var q3: String = ""
    @State private var showThankYou = false

    var body: some View {
        NavigationStack {
            Form {
                Section(header: Text("1. 今日的整体情绪")) {
                    HStack {
                        Text("很差")
                        Slider(value: $q1, in: 1...10, step: 1)
                        Text("很好")
                    }
                    Text("当前分数：\(Int(q1))")
                        .foregroundColor(.gray)
                }
                Section(header: Text("2. 今天遇到的最大挑战是？")) {
                    TextField("请填写…", text: $q2)
                }
                Section(header: Text("3. 您希望获得的支持或建议：")) {
                    TextEditor(text: $q3)
                        .frame(height: 80)
                }
            }
            .navigationTitle("自我评估")
            .toolbar {
                ToolbarItem(placement: .confirmationAction) {
                    Button("提交") {
                        showThankYou = true
                    }
                }
                ToolbarItem(placement: .cancellationAction) {
                    Button("关闭") { dismiss() }
                }
            }
            .alert("感谢您的提交！", isPresented: $showThankYou, actions: {
                Button("好", role: .cancel) { dismiss() }
            }) {
                Text("您的反馈有助于我们更好地支持您。")
            }
        }
    }
}

#Preview {
    AssessmentView()
}
