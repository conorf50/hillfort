package org.cfarrell.hillfort.activities

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hllfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.cfarrell.hillfort.R
import org.cfarrell.hillfort.helpers.readImage
import org.cfarrell.hillfort.helpers.readImageFromPath
import org.cfarrell.hillfort.helpers.showImagePicker
import org.cfarrell.hillfort.main.MainApp
import org.cfarrell.hillfort.models.HillfortModel
import org.cfarrell.hillfort.models.Location

class HillfortActivity : AppCompatActivity(), AnkoLogger {

  var hillfort = HillfortModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hllfort)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Hillfort Activity started..")

    app = application as MainApp
    var edit = false

    if (intent.hasExtra("hillfort edit")) {
      edit = true
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort edit")
      hillfortTitle.setText(hillfort.title)
      description.setText(hillfort.description)
      hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
      if (hillfort.image != null) {
        chooseImage.setText(R.string.change_hillfort_image)
      }
      btnAdd.setText(R.string.save_hillfort)
    }

    btnAdd.setOnClickListener() {
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = description.text.toString()
      if (hillfort.title.isEmpty()) {
        toast(R.string.enter_hillfort_title)
      } else {
        if (edit) {
          app.hillforts.update(hillfort.copy())
        } else {
          app.hillforts.create(hillfort.copy())
        }
      }
      info("add Button Pressed: $hillfortTitle")
      setResult(AppCompatActivity.RESULT_OK)
      // todo display snackbar confiming placemark add
      finish()
    }

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
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
        info { "DELETE BUTTON PRESSED" }
        // todo prompt user before delete!
        // todo add snackbar confirming hillfort deletion
        app.hillforts.delete(hillfort)
        finish()
      }

    }

    return super.onOptionsItemSelected(item)
  }





  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          hillfort.image = data.getData().toString()
          hillfortImage.setImageBitmap(readImage(this, resultCode, data))
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


