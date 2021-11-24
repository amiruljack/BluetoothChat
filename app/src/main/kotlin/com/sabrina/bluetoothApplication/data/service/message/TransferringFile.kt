package com.sabrina.bluetoothApplication.data.service.message

data class TransferringFile(val name: String?, val size: Long, val transferType: TransferType) {
    enum class TransferType { SENDING, RECEIVING }
}
