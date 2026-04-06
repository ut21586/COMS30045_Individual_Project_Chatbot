//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.KeyboardArrowRight
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.withapp.with.models.AppState
//
//@Composable
//fun SettingsView(appState: AppState) {
//    val scrollState = rememberScrollState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .statusBarsPadding()
//            .verticalScroll(scrollState)
//            .padding(20.dp)
//    ) {
//        Text("设置", fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 24.dp))
//
//        // Profile Section
//        SettingsCard {
//            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
//                Box(modifier = Modifier.size(50.dp).background(Color.Pink.copy(alpha = 0.2f), RoundedCornerShape(25.dp)), contentAlignment = Alignment.Center) {
//                    Icon(Icons.Default.Person, null, tint = Color.Pink)
//                }
//                Spacer(modifier = Modifier.width(16.dp))
//                Column {
//                    Text(appState.userName, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
//                    Text("血糖管理中", fontSize = 14.sp, color = Color.Gray)
//                }
//                Spacer(modifier = Modifier.weight(1f))
//                Icon(Icons.Default.KeyboardArrowRight, null, tint = Color.Gray)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Preference Section
//        Text("偏好设置", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(start = 4.dp, bottom = 8.dp))
//        SettingsCard {
//            Column {
//                SettingsRow("聊天伙伴", appState.selectedCharacter.displayName)
//                // Fixed: Changed HorizontalDivider to standard Divider for version compatibility
//                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.LightGray.copy(alpha = 0.3f))
//                SettingsRow("界面语言", appState.appLanguage.displayName)
//            }
//        }
//    }
//}
//
//@Composable
//private fun SettingsCard(content: @Composable () -> Unit) {
//    Surface(
//        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(16.dp),
//        color = Color.White,
//        shadowElevation = 2.dp,
//        content = content
//    )
//}
//
//@Composable
//private fun SettingsRow(title: String, subtitle: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth().padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(title, fontSize = 16.sp)
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text(subtitle, fontSize = 14.sp, color = Color.Gray)
//            Icon(Icons.Default.KeyboardArrowRight, null, tint = Color.Gray, modifier = Modifier.size(16.dp))
//        }
//    }
//}
package com.withapp.with.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.models.AppState

@Composable
fun SettingsView(
    appState: AppState // State derived from MainContainer
) {
    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding().padding(20.dp)
    ) {
        // Header in Chinese
        Text("应用设置", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(30.dp))

        // User status display
        Text("欢迎回来, ${appState.userName}", fontSize = 16.sp)

        Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.LightGray.copy(alpha = 0.5f))

        Text("版本号: 1.0.0 (Android)", fontSize = 14.sp, color = Color.Gray)
    }
}