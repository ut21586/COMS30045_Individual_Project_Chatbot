import Foundation

func tr(_ key: String, language: AppLanguage) -> String {
    let tableName = language == .chinese ? "Localizable-zh" : "Localizable-en"
    return NSLocalizedString(key, tableName: tableName, comment: "")
}
