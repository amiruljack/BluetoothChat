package com.sabrina.bluetoothApplication.di

import com.sabrina.bluetoothApplication.data.database.Database
import com.sabrina.bluetoothApplication.data.model.*
import com.sabrina.bluetoothApplication.ui.view.NotificationView
import com.sabrina.bluetoothApplication.ui.view.NotificationViewImpl
import com.sabrina.bluetoothApplication.ui.viewmodel.converter.ChatMessageConverter
import com.sabrina.bluetoothApplication.ui.viewmodel.converter.ContactConverter
import com.sabrina.bluetoothApplication.ui.viewmodel.converter.ConversationConverter
import com.sabrina.bluetoothApplication.ui.widget.ShortcutManager
import com.sabrina.bluetoothApplication.ui.widget.ShortcutManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val bluetoothConnectionModule = module {
    single { BluetoothConnectorImpl(androidContext()) as BluetoothConnector }
    factory { BluetoothScannerImpl(androidContext()) as BluetoothScanner }
}

val databaseModule = module {
    single { Database.getInstance(androidContext()) }
    single { MessagesStorageImpl(get()) as MessagesStorage }
    single { ConversationsStorageImpl(get()) as ConversationsStorage }
}

val localStorageModule = module {
    single { FileManagerImpl(androidContext()) as FileManager }
    single { UserPreferencesImpl(androidContext()) as UserPreferences }
    single { ProfileManagerImpl(androidContext()) as ProfileManager }
}

const val localeScope = "locale_scope"

val viewModule = module {
    single { NotificationViewImpl(androidContext()) as NotificationView }
    single { ShortcutManagerImpl(androidContext()) as ShortcutManager }
    scope(localeScope) { ContactConverter() }
    scope(localeScope) { ConversationConverter(androidContext()) }
    scope(localeScope) { ChatMessageConverter(androidContext()) }
}
