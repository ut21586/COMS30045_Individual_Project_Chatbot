//
//package com.withapp.with.ui.screens
//
//import androidx.compose.animation.Crossfade
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.composed
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.withapp.with.models.AppState
//import com.withapp.with.viewmodels.ChatViewModel
//import com.withapp.with.viewmodels.ReportViewModel
//
//@Composable
//fun MainContainer(
//    appState: AppState = viewModel(),
//    onRequestPermission: () -> Unit = {}
//) {
//    // 核心修复：将这里的 targetState 替换为你 Swift 源码里原生的 isOnboarded！
//    Crossfade(targetState = appState.isOnboarded, label = "app_flow") { completed ->
//        if (!completed) {
//            OnboardingView(appState = appState)
//        } else {
//            MainAppLayout(appState = appState, onRequestPermission = onRequestPermission)
//        }
//    }
//}
//
//@Composable
//fun MainAppLayout(appState: AppState, onRequestPermission: () -> Unit) {
//    var selectedTab by remember { mutableStateOf("home") }
//    val chatViewModel: ChatViewModel = viewModel()
//    val reportViewModel: ReportViewModel = viewModel()
//
//    val bgGradient = Brush.verticalGradient(
//        colors = listOf(Color(0xFFF2F9F7), Color(0xFFE6F2F2))
//    )
//
//    Box(modifier = Modifier.fillMaxSize().background(bgGradient)) {
//        val bottomPadding = if (selectedTab == "chat" || selectedTab == "device") 0.dp else 80.dp
//
//        Box(modifier = Modifier.fillMaxSize().padding(bottom = bottomPadding)) {
//            when (selectedTab) {
//                "home" -> HomeView(
//                    appState = appState,
//                    safePadding = PaddingValues(0.dp),
//                    onNavigateToChat = { msg ->
//                        chatViewModel.sendMessage(msg)
//                        selectedTab = "chat"
//                    },
//                    onRequestPermission = onRequestPermission
//                )
//                "chat" -> ChatView(
//                    chatViewModel = chatViewModel,
//                    onBack = { selectedTab = "home" }
//                )
//                "report" -> ReportView(reportViewModel = reportViewModel)
//                "settings" -> SettingsView(
//                    appState = appState,
//                    onNavigateToDevice = { selectedTab = "device" }
//                )
//                "device" -> DeviceConnectionView(
//                    onBack = { selectedTab = "settings" }
//                )
//            }
//        }
//
//        if (selectedTab != "chat" && selectedTab != "device") {
//            Box(modifier = Modifier.align(Alignment.BottomCenter).padding(horizontal = 20.dp, vertical = 24.dp)) {
//                Surface(
//                    modifier = Modifier.fillMaxWidth().height(64.dp).shadow(10.dp, RoundedCornerShape(32.dp)),
//                    shape = RoundedCornerShape(32.dp),
//                    color = Color.White.copy(alpha = 0.95f)
//                ) {
//                    Row(
//                        modifier = Modifier.fillMaxSize(),
//                        horizontalArrangement = Arrangement.SpaceAround,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        TabItem(Icons.Default.Home, "首页", selectedTab == "home") { selectedTab = "home" }
//                        TabItem(Icons.Default.Email, "聊天", selectedTab == "chat") { selectedTab = "chat" }
//                        TabItem(Icons.Default.DateRange, "报告", selectedTab == "report") { selectedTab = "report" }
//                        TabItem(Icons.Default.Settings, "设置", selectedTab == "settings") { selectedTab = "settings" }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun TabItem(
//    icon: androidx.compose.ui.graphics.vector.ImageVector,
//    label: String,
//    isSelected: Boolean,
//    onClick: () -> Unit
//) {
//    val color = if (isSelected) Color(0xFF008080) else Color.Gray
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.clickableNoRipple(onClick)
//    ) {
//        Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
//        Text(label, fontSize = 10.sp, color = color)
//    }
//}
//
//fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier = composed {
//    this.then(
//        Modifier.clickable(
//            interactionSource = remember { MutableInteractionSource() },
//            indication = null,
//            onClick = onClick
//        )
//    )
//}

package com.withapp.with.ui.screens

import androidx.compose.animation.Crossfade
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.withapp.with.models.AppState
import com.withapp.with.viewmodels.ChatViewModel
import com.withapp.with.viewmodels.ReportViewModel

@Composable
fun MainContainer(
    appState: AppState = viewModel(),
    onRequestPermission: () -> Unit = {}
) {
    Crossfade(targetState = appState.isOnboarded, label = "app_flow") { completed ->
        if (!completed) {
            OnboardingView(appState = appState)
        } else {
            MainAppLayout(appState = appState, onRequestPermission = onRequestPermission)
        }
    }
}

@Composable
fun MainAppLayout(appState: AppState, onRequestPermission: () -> Unit) {
    var selectedTab by remember { mutableStateOf("home") }
    val chatViewModel: ChatViewModel = viewModel()
    val reportViewModel: ReportViewModel = viewModel()

    val bgGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFF2F9F7), Color(0xFFE6F2F2))
    )

    Box(modifier = Modifier.fillMaxSize().background(bgGradient)) {
        // 当进入聊天、设备或设置页时，隐藏底部导航栏
        val bottomPadding = if (selectedTab == "chat" || selectedTab == "device" || selectedTab == "settings") 0.dp else 80.dp

        Box(modifier = Modifier.fillMaxSize().padding(bottom = bottomPadding)) {
            when (selectedTab) {
                "home" -> HomeView(
                    appState = appState,
                    safePadding = PaddingValues(0.dp),
                    onNavigateToChat = { msg ->
                        chatViewModel.sendMessage(msg)
                        selectedTab = "chat"
                    },
                    onNavigateToSettings = { selectedTab = "settings" }, // 左上角触发
                    onRequestPermission = onRequestPermission
                )
                "chat" -> ChatView(
                    chatViewModel = chatViewModel,
                    onBack = { selectedTab = "home" }
                )
                "report" -> ReportView(reportViewModel = reportViewModel)
                "settings" -> SettingsView(
                    appState = appState,
                    onBack = { selectedTab = "home" }, // 从设置页返回首页
                    onNavigateToDevice = { selectedTab = "device" }
                )
                "device" -> DeviceConnectionView(
                    onBack = { selectedTab = "settings" }
                )
            }
        }

        // 底部导航栏（已移除最右侧的设置按钮）
        if (selectedTab != "chat" && selectedTab != "device" && selectedTab != "settings") {
            Box(modifier = Modifier.align(Alignment.BottomCenter).padding(horizontal = 40.dp, vertical = 24.dp)) {
                Surface(
                    modifier = Modifier.fillMaxWidth().height(64.dp).shadow(10.dp, RoundedCornerShape(32.dp)),
                    shape = RoundedCornerShape(32.dp),
                    color = Color.White.copy(alpha = 0.95f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly, // 均匀分布剩下的3个按钮
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TabItem(Icons.Default.Home, "首页", selectedTab == "home") { selectedTab = "home" }
                        TabItem(Icons.Default.Email, "聊天", selectedTab == "chat") { selectedTab = "chat" }
                        TabItem(Icons.Default.DateRange, "报告", selectedTab == "report") { selectedTab = "report" }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.TabItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSelected) Color(0xFF008080) else Color.Gray
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.weight(1f).clickableNoRipple(onClick) // 让3个按钮等宽分布
    ) {
        Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Text(label, fontSize = 10.sp, color = color)
    }
}

fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier = composed {
    this.then(
        Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    )
}