package com.sabrina.bluetoothApplication.ui.viewmodel

import androidx.annotation.StringRes
import com.sabrina.bluetoothApplication.data.service.message.PayloadType
import com.sabrina.bluetoothApplication.utils.Size

data class ChatMessageViewModel(
        val uid: Long,
        val dayOfYear: String,
        val dayOfYearRaw: Long,
        val time: String,
        val text: String?,
        val own: Boolean,
        val type: PayloadType?,
        val isImageAvailable: Boolean,
        @StringRes
        val imageProblemText: Int,
        val imageSize: Size,
        val imagePath: String?,
        val imageUri: String?
)
