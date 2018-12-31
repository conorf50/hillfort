package org.cfarrell.hillfort.activities

import android.content.Intent
import org.jetbrains.anko.intentFor
import org.cfarrell.hillfort.helpers.showImagePicker
import org.cfarrell.hillfort.main.MainApp
import org.cfarrell.hillfort.models.Location
import org.cfarrell.hillfort.models.HillfortModel

class PlacemarkPresenter(val activity: HillfortActivity) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var hillfort = HillfortModel()
    var location = Location(52.245696, -7.139102, 15f)
    var app: MainApp
    var edit = false;

    init {
        app = activity.application as MainApp
        if (activity.intent.hasExtra("placemark_edit")) {
            edit = true
            hillfort = activity.intent.extras.getParcelable<HillfortModel>("hillfort_edit")
            activity.showHillfort(hillfort)
        }
    }

    fun doAddOrSave(title: String, description: String) {
        hillfort.title = title
        hillfort.description = description
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
        when (requestCode) {
            IMAGE_REQUEST -> {
                //hillfort.image = data.data.toString()
                activity.showHillfort(hillfort)
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