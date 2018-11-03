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

//fun readImage(activity: Activity, resultCode: Int, data: Intent?): Bitmap? {
//  var bitmap: Bitmap? = null
//  if (resultCode == Activity.RESULT_OK && data != null && data.data != null) try {
//    bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, data.data)
//  } catch (e: IOException) {
//    e.printStackTrace()
//  }
//  return bitmap
//}





// gets a preview of the image and displays it to the user in the hillfort card.
// todo rewrite this to return the first element in the array if it exists
fun readImageFromPath(context: Context, path: ArrayList<String>): Bitmap? {
  var bitmap: Bitmap? = null
    // set the image bitmap to be the first in the hillfort array object
  //val uri = Uri.parse(path.first())
  val uri  = Uri.parse("")
  if (uri != null) {
    try {
      val parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r")
      val fileDescriptor = parcelFileDescriptor.getFileDescriptor()
      bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
      parcelFileDescriptor.close()
    } catch (e: Exception) {
    }
  }
  return bitmap
}