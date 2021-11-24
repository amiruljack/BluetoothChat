package com.sabrina.bluetoothApplication.ui.view

import com.sabrina.bluetoothApplication.ui.viewmodel.ContactViewModel

interface ContactChooserView {
    fun showContacts(contacts: List<ContactViewModel>)
    fun showNoContacts()
}
