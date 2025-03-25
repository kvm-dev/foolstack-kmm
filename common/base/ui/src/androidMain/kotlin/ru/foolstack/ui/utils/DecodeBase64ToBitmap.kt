package ru.foolstack.ui.utils

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap



//fun String.decodeBase64ToBitmap(): ImageBitmap? {
//    return try {
//        val imageBytes = Base64.decode(this, Base64.DEFAULT)
//        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//        bitmap.asImageBitmap()
//    } catch (e: Exception) {
//        null
//    }
//}


fun String.decodeBase64ToBitmap(): ImageBitmap? {
    return try {
        val imageBytes = Base64.decode(this.substringAfter("base64,"), Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}



