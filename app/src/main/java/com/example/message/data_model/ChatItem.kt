package com.example.message.data_model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatItem(
    @Json(name = "id") val id: Int,
    @Json(name = "image") val image: String?,
    @Json(name = "owner") val owner: String,
    @Json(name = "last_message") val lastMessage: String,
    @Json(name = "last_active") val lastActive: String,
    @Json(name = "unread_messages") val unreadMessages: Int,
    @Json(name = "is_typing") val isTyping: Boolean,
    @Json(name = "laste_message_type") val lastMessageType: String
)

enum class MessageType(val value: String) {
    TEXT(""),
    FILE("Sent an attachment"),
    VOICE("Sent a voice message")
}