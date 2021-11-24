package com.sabrina.bluetoothApplication.ui.viewmodel.converter

import com.amulyakhare.textdrawable.TextDrawable
import com.sabrina.bluetoothApplication.data.entity.Conversation
import com.sabrina.bluetoothApplication.ui.viewmodel.ContactViewModel
import com.sabrina.bluetoothApplication.utils.getFirstLetter

class ContactConverter {

    fun transform(conversation: Conversation): ContactViewModel {
        return ContactViewModel(
                conversation.deviceAddress,
                "${conversation.displayName} (${conversation.deviceName})",
                TextDrawable.builder()
                        .buildRound(conversation.displayName.getFirstLetter(), conversation.color)
        )
    }

    fun transform(conversationCollection: Collection<Conversation>): List<ContactViewModel> {
        return conversationCollection.map {
            transform(it)
        }
    }
}
