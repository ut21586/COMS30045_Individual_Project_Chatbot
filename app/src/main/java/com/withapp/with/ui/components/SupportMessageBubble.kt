//package com.withapp.with.ui.components
//
//import androidx.compose.animation.*
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//// Component for displaying the supportive message
//@Composable
//fun SupportMessageBubble(message: String) {
//    var visible by remember { mutableStateOf(false) }
//
//    // Trigger animation when message changes
//    LaunchedEffect(message) {
//        visible = true
//    }
//
//    AnimatedVisibility(
//        visible = visible,
//        enter = fadeIn() + expandVertically()
//    ) {
//        Box(
//            modifier = Modifier
//                .padding(horizontal = 32.dp)
//                .shadow(8.dp, RoundedCornerShape(20.dp))
//                .background(Color.White, RoundedCornerShape(20.dp))
//                .padding(horizontal = 24.dp, vertical = 16.dp)
//        ) {
//            Text(
//                text = message,
//                fontSize = 15.sp,
//                color = Color(0xFF333333),
//                lineHeight = 22.sp
//            )
//        }
//    }
//}

package com.withapp.with.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.withapp.with.models.ContextMode
import com.withapp.with.services.GlucoseLevel

enum class SupportLevel { STABLE, BORDERLINE, CONCERNING }

data class SupportMessage(val text: String, val level: SupportLevel) {
    companion object {
        val defaultMessage = SupportMessage("与你同在。", SupportLevel.STABLE)

        fun getMessage(glucoseLevel: GlucoseLevel, context: ContextMode): SupportMessage {
            return when (glucoseLevel) {
                GlucoseLevel.LOW, GlucoseLevel.HIGH -> SupportMessage("请注意你的血糖变化，需要休息一下吗？", SupportLevel.CONCERNING)
                GlucoseLevel.BORDERLINE_LOW, GlucoseLevel.BORDERLINE_HIGH -> SupportMessage("血糖略有波动，记得按时饮食哦。", SupportLevel.BORDERLINE)
                GlucoseLevel.NORMAL -> contextAwareMessage(context)
                GlucoseLevel.UNKNOWN -> defaultMessage
            }
        }

        private fun contextAwareMessage(context: ContextMode): SupportMessage {
            return when (context) {
                ContextMode.RESTAURANT -> SupportMessage("享受美食的同时，别忘了预估碳水哦。", SupportLevel.STABLE)
                ContextMode.WORK -> SupportMessage("工作再忙，也要记得喝水和走动。", SupportLevel.STABLE)
                ContextMode.SOCIAL -> SupportMessage("和朋友聚会很开心吧？我在后台守护你。", SupportLevel.STABLE)
                else -> SupportMessage("你的状态很稳定，继续保持！", SupportLevel.STABLE)
            }
        }
    }
}

@Composable
fun SupportMessageBubble(message: SupportMessage) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(message) {
        isVisible = true
    }

    AnimatedVisibility(visible = isVisible, enter = fadeIn()) {
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .shadow(8.dp, GenericShape { size, _ ->
                    val cornerRadius = 40f
                    val arrowSize = 24f
                    val arrowX = size.width / 2f

                    // 纯代码绘制 iOS 风格的带尾巴对话气泡
                    addRoundRect(androidx.compose.ui.geometry.RoundRect(0f, 0f, size.width, size.height - arrowSize, androidx.compose.ui.geometry.CornerRadius(cornerRadius)))
                    moveTo(arrowX - arrowSize, size.height - arrowSize)
                    lineTo(arrowX, size.height)
                    lineTo(arrowX + arrowSize, size.height - arrowSize)
                })
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(message.text, fontSize = 15.sp, color = Color.Black, textAlign = TextAlign.Center)
                Text("With", fontSize = 11.sp, color = Color(0xFF008080).copy(alpha = 0.7f))
            }
        }
    }
}