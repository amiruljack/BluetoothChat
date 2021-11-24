package com.sabrina.bluetoothApplication.ui.view

import com.sabrina.bluetoothApplication.data.entity.MessageFile

interface ReceivedImagesView {
    fun displayImages(imageMessages: List<MessageFile>)
    fun showNoImages()
}
