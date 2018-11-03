package org.cfarrell.hillfort.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hllfort.*
import kotlinx.android.synthetic.main.notification_media_cancel_action.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.cfarrell.hillfort.R
import org.cfarrell.hillfort.helpers.readImageFromPath
import org.cfarrell.hillfort.helpers.showImagePicker
import org.cfarrell.hillfort.main.MainApp
import org.cfarrell.hillfort.models.HillfortModel
import org.cfarrell.hillfort.models.Location
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.abc_activity_chooser_view.*
import org.cfarrell.hillfort.helpers.ImageViewPagerHelper

class HillfortActivity : AppCompatActivity(), AnkoLogger {

  var hillfort = HillfortModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2
  private val imageUrls = arrayListOf<String>()


  override fun onCreate(savedInstanceState: Bundle?) {
  //imageUrls.add("https://cdn.pixabay.com/photo/2017/10/10/15/28/butterfly-2837589_960_720.jpg")
//    imageUrls.add("https://cdn.pixabay.com/photo/2017/12/24/09/09/road-3036620_960_720.jpg")
    imageUrls.clear() // clear the image urls array
    hillfort.image.clear() // clear the hillfort image so old images are purged
     super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hllfort)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)

    app = application as MainApp
    var edit = false

    if (intent.hasExtra("hillfort edit")) {
      val viewPager= findViewById<ViewPager>(R.id.view_pager)

      edit = true
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort edit")
      hillfortTitle.setText(hillfort.title)
      description.setText(hillfort.description)

      // add the hillfort image to the viewpager



      //imageUrls.add(hillfort.image.toString())
        hillfort.image.forEach {
            imageUrls.add(it)
        }
      toast("image array size " + imageUrls.size)


//      toast("hilfort image " + imageUrls.toString())
      //here the imageView is being populated with the list of image URIs
      val adapter = ImageViewPagerHelper(this, hillfort.image)
      viewPager.setAdapter(adapter)
      adapter.notifyDataSetChanged() // update the viewpager

      if (hillfort.image != null) {
        chooseImage.setText(R.string.change_hillfort_image)
      }
      btnAdd.setText(R.string.save_hillfort)
    }

    btnAdd.setOnClickListener() {view ->
//      val viewPager= findViewById<ViewPager>(R.id.view_pager)
//
//      val adapter = ImageViewPagerHelper(this, imageUrls)
//      viewPager.setAdapter(adapter)
//
//      adapter.notifyDataSetChanged() //update the viewpager view with the new image
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = description.text.toString()

      // todo fix the activity close on incorrect title
      if (hillfort.title.isEmpty()) {
        toast(R.string.enter_hillfort_title)
      }

      else {
        if (edit) {
          app.hillforts.update(hillfort.copy())
          finish()

        } else {
          app.hillforts.create(hillfort.copy())
          finish()

        }
      }
      info("add Button Pressed: $hillfortTitle")
      setResult(AppCompatActivity.RESULT_OK)


    }

    chooseImage.setOnClickListener {
//      val viewPager= findViewById<ViewPager>(R.id.view_pager)
//      val adapter = ImageViewPagerHelper(this, imageUrls)

        showImagePicker(this, IMAGE_REQUEST)
//      viewPager.setAdapter(adapter)
//
//      adapter.notifyDataSetChanged() //update the viewpager view with the new image
    }


    // todo add delete button + definition
    hillfortLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (hillfort.zoom != 0f) {
        location.lat = hillfort.lat
        location.lng = hillfort.lng
        location.zoom = hillfort.zoom
      }
      startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {

    when (item?.itemId) {
      // when the cancel button is pressed on the menu, kill this activity and return to previous activity
      R.id.item_cancel -> {
        finish()
      }

      R.id.item_delete -> {
        // create a dialog box that prompts the user to confirm the delete
        // source: https://code.tutsplus.com/tutorials/showing-material-design-dialogs-in-an-android-app--cms-30013
        AlertDialog.Builder(this)
                .setTitle("Delete Hillfort?")
                .setMessage("Are you aure that you want to delete this hillfort entry?.")
                .setPositiveButton("Yes") { dialog, which -> app.hillforts.delete(hillfort)
                    finish()
                }
                .setNegativeButton("Cancel") { dialog, which -> cancel_action
                }
                .show()
        info { "DELETE BUTTON PRESSED" }

        // todo add snackbar confirming hillfort deletion


      }

    }

    return super.onOptionsItemSelected(item)
  }



  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    val viewPager= findViewById<ViewPager>(R.id.view_pager)

    when (requestCode) {
      // when we request an image update
      IMAGE_REQUEST -> {
        if (data != null) {
          hillfort.image.clear() // clear the hillfort image so old images are purged
          if (imageUrls.size < 5) {
            imageUrls.add( data.getData().toString())
            hillfort.image = imageUrls
          }
          else{
            toast("Max amount of images added")
          }
          // set the displayed image to the new one selected
          //hillfortImage.setImageBitmap(readImage(this, resultCode, data))

          // call the viewpager object
          val adapter = ImageViewPagerHelper(this, hillfort.image)
            viewPager.setAdapter(adapter)

            adapter.notifyDataSetChanged() //update the viewpager view with the new image
          chooseImage.setText(R.string.change_hillfort_image)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras.getParcelable<Location>("location")
          hillfort.lat = location.lat
          hillfort.lng = location.lng
          hillfort.zoom = location.zoom
        }
      }
    }
  }
}


