package com.sabrina.bluetoothApplication.data.model

import com.sabrina.bluetoothApplication.data.entity.Conversation
import com.sabrina.bluetoothApplication.data.entity.ConversationWithMessages

interface ConversationsStorage {
    suspend fun getContacts(): List<Conversation>
    suspend fun getConversations(): List<ConversationWithMessages>
    suspend fun getConversationByAddress(address: String): Conversation?
    suspend fun insertConversation(conversation: Conversation)
    suspend fun removeConversationByAddress(address: String)
}
