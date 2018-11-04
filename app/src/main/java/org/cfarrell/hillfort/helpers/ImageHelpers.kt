package org.cfarrell.hillfort.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import org.cfarrell.hillfort.R
import java.io.IOException

fun showImagePicker(parent: Activity, id: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    val chooser = Intent.createChooser(intent, R.string.select_hillfort_image.toString())
    parent.startActivityForResult(chooser, id)
}


fun readImageFromPath(context: Context, path: ArrayList<String>): Bitmap? {
    var bitmap: Bitmap? = null
    // set the image bitmap to be the first in the hillfort array object
    //val uri = Uri.parse(path.first())

    try {
        val uri = Uri.parse(path.first()) // parse the first element if it exists
        if (uri != null) {
            try {
                val parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r")
                val fileDescriptor = parcelFileDescriptor.getFileDescriptor()
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                parcelFileDescriptor.close()
            } catch (e: Exception) {
            }
        }
    } catch (e: java.lang.Exception) {
        val uri = Uri.parse("") // set the URI to be blank otherwise
    }

    return bitmap
}