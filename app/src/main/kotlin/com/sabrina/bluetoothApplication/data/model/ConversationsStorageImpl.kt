package com.sabrina.bluetoothApplication.data.model

import com.sabrina.bluetoothApplication.data.database.ChatDatabase
import com.sabrina.bluetoothApplication.data.entity.Conversation

class ConversationsStorageImpl(db: ChatDatabase) : ConversationsStorage {

    private val dao = db.conversationsDao()

    override suspend fun getContacts() = dao.getContacts()

    override suspend fun getConversations() = dao.getAllConversationsWithMessages()

    override suspend fun getConversationByAddress(address: String) = dao.getConversationByAddress(address)

    override suspend fun insertConversation(conversation: Conversation) {
        dao.insert(conversation)
    }

    override suspend fun removeConversationByAddress(address: String) {
        dao.delete(address)
    }
}
