package com.withapp.with.network

import kotlinx.coroutines.delay

data class ChatRequest(val message: String)
data class ChatResponse(val reply: String)


object MockBackend {
    suspend fun sendMessage(request: ChatRequest): ChatResponse {
        delay(1500)
        return ChatResponse(reply = "你好！我是你的健康伴侣 With 🤖。\n\n(离线演示版已激活，收到消息：${request.message})")
    }
}