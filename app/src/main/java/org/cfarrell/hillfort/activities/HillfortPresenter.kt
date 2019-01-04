package org.cfarrell.hillfort.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.Manifest
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_hllfort.*
import kotlinx.android.synthetic.main.notification_media_cancel_action.*
import org.jetbrains.anko.AnkoLogger
import android.content.pm.PackageManager
import android.widget.RatingBar
import org.cfarrell.hillfort.R

import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.cfarrell.hillfort.helpers.showImagePicker
import org.cfarrell.hillfort.main.MainApp
import org.cfarrell.hillfort.models.HillfortModel
import org.cfarrell.hillfort.models.Location
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import org.cfarrell.hillfort.R.id.chooseImage
import org.cfarrell.hillfort.helpers.ImageViewPagerHelper
import java.time.Duration
import java.util.*

class HillfortPresenter(val activity: HillfortActivity) {

    var hillfort = HillfortModel()
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    val IMAGE_DELETE_REQUEST = 3
    private val imageUrls = arrayListOf<String>()
    var location = Location(52.245696, -7.139102, 15f)
    var app: MainApp
    var edit = false;
   // val viewPager = findViewById<ViewPager>(R.id.view_pager)




    init {
        app = activity.application as MainApp
        if (activity.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = activity.intent.extras.getParcelable<HillfortModel>("hillfort edit")
            activity.showHillfort(hillfort)
        }
    }
    fun doAddOrSave(title: String, description: String, visitedDate : Date, visitedFlag : Boolean, favouriteFlag: Boolean, rating: Int) {
        hillfort.title = title
        hillfort.description = description
        hillfort.visitedDate = visitedDate
        hillfort.visitedFlag = visitedFlag
        hillfort.favouriteFlag= favouriteFlag
        hillfort.rating = rating
        if (edit) {
            app.hillforts.update(hillfort)
        } else {
            app.hillforts.create(hillfort)
        }
        activity.finish()
    }
    fun doCancel() {
        activity.finish()
    }

    fun doDelete() {
        app.hillforts.delete(hillfort)
        activity.finish()
    }

    fun doSelectImage() {
        showImagePicker(activity, IMAGE_REQUEST)
    }

    fun doSetLocation() {
        if (hillfort.zoom != 0f) {
            location.lat = hillfort.lat
            location.lng = hillfort.lng
            location.zoom = hillfort.zoom
        }
        activity.startActivityForResult(activity.intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }


    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        //val viewPager = findViewById<ViewPager>(R.id.view_pager)
        imageUrls.clear()
        when (requestCode) {
            IMAGE_REQUEST -> {
                activity.showHillfort(hillfort)
                if (data != null) {
                    imageUrls.add(data.getData().toString())
                    imageUrls.forEach { hillfort.image.add(it) }
                    // call the viewpager object
                    //val adapter = ImageViewPagerHelper(this, hillfort.image)
                   // viewPager.setAdapter(adapter)

                    //adapter.notifyDataSetChanged() //update the viewpager view with the new image
                    //chooseImage.setText(R.string.change_hillfort_image)
                }


            }
            LOCATION_REQUEST -> {
                location = data.extras.getParcelable<Location>("location")
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
            }
        }
    }
}