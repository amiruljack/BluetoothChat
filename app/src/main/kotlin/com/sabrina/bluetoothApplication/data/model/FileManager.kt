package com.sabrina.bluetoothApplication.data.model

import android.net.Uri

interface FileManager {
    suspend fun extractApkFile(): Uri?
}
