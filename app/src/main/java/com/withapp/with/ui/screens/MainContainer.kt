//package com.withapp.with.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//
//// --- 必须手动对齐的导入 ---
//import com.withapp.with.models.AppState
//import com.withapp.with.viewmodels.ChatViewModel
//import com.withapp.with.viewmodels.ReportViewModel
//
//@Composable
//fun MainContainer(
//    appState: AppState = viewModel(),
//    onRequestPermission: () -> Unit
//) {
//    var selectedTab by remember { mutableStateOf("home") }
//    val chatViewModel: ChatViewModel = viewModel()
//    val reportViewModel: ReportViewModel = viewModel()
//
//    val bgGradient = Brush.verticalGradient(
//        colors = listOf(Color(0xFFF2F9F7), Color(0xFFE6F2F2))
//    )
//
//    Box(modifier = Modifier.fillMaxSize().background(bgGradient)) {
//        val bottomPadding = if (selectedTab == "chat") 0.dp else 80.dp
//
//        Box(modifier = Modifier.fillMaxSize().padding(bottom = bottomPadding)) {
//            when (selectedTab) {
//                "home" -> HomeView(
//                    safePadding = PaddingValues(0.dp),
//                    onNavigateToChat = { msg ->
//                        chatViewModel.sendMessage(msg)
//                        selectedTab = "chat"
//                    },
//                    onRequestPermission = onRequestPermission
//                )
//                "chat" -> ChatView(chatViewModel = chatViewModel, onBack = { selectedTab = "home" })
//                "report" -> ReportView(reportViewModel = reportViewModel)
//                "settings" -> SettingsView(appState = appState)
//            }
//        }
//
//        if (selectedTab != "chat") {
//            Box(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(horizontal = 20.dp, vertical = 24.dp)
//            ) {
//                Surface(
//                    modifier = Modifier.fillMaxWidth().height(64.dp).shadow(10.dp, RoundedCornerShape(32.dp)),
//                    shape = RoundedCornerShape(32.dp), color = Color.White.copy(alpha = 0.95f)
//                ) {
//                    Row(
//                        modifier = Modifier.fillMaxSize(),
//                        horizontalArrangement = Arrangement.SpaceAround,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        TabBarItem(Icons.Default.Home, "首页", selectedTab == "home") { selectedTab = "home" }
//                        TabBarItem(Icons.Default.Email, "聊天", selectedTab == "chat") { selectedTab = "chat" }
//                        TabBarItem(Icons.Default.DateRange, "报告", selectedTab == "report") { selectedTab = "report" }
//                        TabBarItem(Icons.Default.Settings, "设置", selectedTab == "settings") { selectedTab = "settings" }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun TabBarItem(icon: ImageVector, label: String, isSelected: Boolean, onClick: () -> Unit) {
//    val color = if (isSelected) Color(0xFF008080) else Color.Gray
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.clickable { onClick() }
//    ) {
//        Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
//        Text(label, fontSize = 10.sp, color = color)
//    }
//}

package com.withapp.with.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Imports for state and viewmodels. Ensure package names match your project.
import com.withapp.with.models.AppState
import com.withapp.with.viewmodels.ChatViewModel
import com.withapp.with.viewmodels.ReportViewModel

@Composable
fun MainContainer(
    appState: AppState = viewModel(),
    onRequestPermission: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("home") }

    // Initialize ViewModels to persist data across navigation tabs
    val chatViewModel: ChatViewModel = viewModel()
    val reportViewModel: ReportViewModel = viewModel()

    // Background gradient matching the iOS aesthetic
    val bgGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFF2F9F7), Color(0xFFE6F2F2))
    )

    Box(modifier = Modifier.fillMaxSize().background(bgGradient)) {
        // Remove bottom padding in chat to allow full screen keyboard interaction
        val bottomPadding = if (selectedTab == "chat") 0.dp else 80.dp

        Box(modifier = Modifier.fillMaxSize().padding(bottom = bottomPadding)) {
            when (selectedTab) {
                "home" -> HomeView(
                    safePadding = PaddingValues(0.dp),
                    onNavigateToChat = { msg ->
                        chatViewModel.sendMessage(msg)
                        selectedTab = "chat"
                    },
                    onRequestPermission = onRequestPermission
                )
                "chat" -> ChatView(
                    chatViewModel = chatViewModel,
                    onBack = { selectedTab = "home" }
                )
                "report" -> ReportView(
                    reportViewModel = reportViewModel
                )
                "settings" -> SettingsView(
                    appState = appState
                )
            }
        }

        // Custom Floating Tab Bar: Hidden when chat is active
        if (selectedTab != "chat") {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .shadow(10.dp, RoundedCornerShape(32.dp)),
                    shape = RoundedCornerShape(32.dp),
                    color = Color.White.copy(alpha = 0.95f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TabItem(Icons.Default.Home, "首页", selectedTab == "home") { selectedTab = "home" }
                        TabItem(Icons.Default.Email, "聊天", selectedTab == "chat") { selectedTab = "chat" }
                        TabItem(Icons.Default.DateRange, "报告", selectedTab == "report") { selectedTab = "report" }
                        TabItem(Icons.Default.Settings, "设置", selectedTab == "settings") { selectedTab = "settings" }
                    }
                }
            }
        }
    }
}

// Fixed the syntax error here. Modifier is now correctly chained.
@Composable
private fun TabItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSelected) Color(0xFF008080) else Color.Gray
    // Creates a customized interaction source to disable the ripple effect
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
    ) {
        Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
        Text(label, fontSize = 10.sp, color = color)
    }
}
