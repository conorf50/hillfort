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
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.cfarrell.hillfort.R
import org.cfarrell.hillfort.helpers.showImagePicker
import org.cfarrell.hillfort.main.MainApp
import org.cfarrell.hillfort.models.HillfortModel
import org.cfarrell.hillfort.models.Location
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import org.cfarrell.hillfort.helpers.ImageViewPagerHelper
import java.time.Duration
import java.util.*


class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    val IMAGE_DELETE_REQUEST = 3
    private val imageUrls = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        imageUrls.clear() // clear the image urls array
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hllfort)
        //toolbarAdd.title = title
       // setSupportActionBar(toolbarAdd)
        val buttonDeleteImage: View = findViewById(R.id.deleteImage)
        // hide the delete button for now as it causes bugs
        buttonDeleteImage.setVisibility(View.GONE)

        // see this at bottom of file.
        // Source: https://www.techotopia.com/index.php/Kotlin_-_Making_Runtime_Permission_Requests_in_Android
        //setupPermissions()

        info("Hillfort Activity started..")

        app = application as MainApp
        var edit = false
        //toast("hillfort" + hillfort)

        if (intent.hasExtra("hillfort edit")) {
            val viewPager = findViewById<ViewPager>(R.id.view_pager)
            val checkbox = findViewById<CheckBox>(R.id.checkBoxHillfortVisited)
            edit = true
            hillfort = intent.extras.getParcelable<HillfortModel>("hillfort edit")
            hillfortTitle.setText(hillfort.title)
            hillfortDescription.setText(hillfort.description)
            checkbox.setChecked(hillfort.visitedFlag)
            //toast("HF Image" + hillfort.image)
            // add the hillfort image to the viewpager
            //imageUrls = hillfort.image)
            hillfort.image.forEach { imageUrls.add(it) }

            //toast("hilfort image " + imageUrls.toString())
            //here the imageView is being populated with the list of image URIs
            val adapter = ImageViewPagerHelper(this, imageUrls)
            viewPager.setAdapter(adapter)
            adapter.notifyDataSetChanged() // update the viewpager

            if (hillfort.image != null) {
                chooseImage.setText(R.string.change_hillfort_image)
            }
            btnAdd.setText(R.string.save_hillfort)
        }

        btnAdd.setOnClickListener() { view ->

            //      adapter.notifyDataSetChanged() //update the viewpager view with the new image
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = hillfortDescription.text.toString()
            hillfort.visitedDate = Date() // set the date to right now
            hillfort.visitedFlag = checkBoxHillfortVisited.isChecked // depending on whether the box is checked
            // add the images from the imageUrl array to hillfort.image
            if (hillfort.title.isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {
                if (edit) {

                    app.hillforts.update(hillfort.copy())
                    finish()

                } else {
                    // todo hide delete button on hf add
                    app.hillforts.create(hillfort.copy())
                    finish()

                }
            }
            info("add Button Pressed: $hillfortTitle")
            setResult(AppCompatActivity.RESULT_OK)
            // todo display snackbar confiming placemark add
            // todo add this snackbar to the hillfortList view instead of this one
            Snackbar.make(view,"Hillfort added", Snackbar.LENGTH_SHORT)

        }




        // listener for the deleteImage button

        buttonDeleteImage.setOnClickListener { view ->

            val viewPager = findViewById<ViewPager>(R.id.view_pager)

            //toast("deleting image at" + viewPager.currentItem)
            // hillfort image is currently null

        }

        chooseImage.setOnClickListener {
            // this is a check to limit the amount of images a user can add
            if (imageUrls.size <= 4) {
                showImagePicker(this, IMAGE_REQUEST)
            } else {
                toast("Max amount of images selected")
            }

        }


        hillfortLocation.setOnClickListener {
            // this is the default location of WIT
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
                        .setMessage("Are you sure that you want to delete this hillfort entry?")
                        .setPositiveButton("Yes") { dialog, which ->
                            app.hillforts.delete(hillfort)
                            finish()
                        }
                        .setNegativeButton("Cancel") { dialog, which ->
                            cancel_action
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
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        imageUrls.clear()

        //when the Change image button is pressed
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    imageUrls.add(data.getData().toString())
                    imageUrls.forEach { hillfort.image.add(it) }
                    // call the viewpager object
                    val adapter = ImageViewPagerHelper(this, hillfort.image)
                    viewPager.setAdapter(adapter)

                    adapter.notifyDataSetChanged() //update the viewpager view with the new image
                    chooseImage.setText(R.string.change_hillfort_image)
                }
            }
//        IMAGE_DELETE_REQUEST -> { // when we want to delete a hilfort image
//            if (data != null) {
//                imageUrls.add( data.getData().toString())
//                //hillfort.image = imageUrls
//                // remove the item at the same index as the viewpager location
//                imageUrls.removeAt(viewPager.currentItem)
//
//
//                // call the viewpager object
//                val adapter = ImageViewPagerHelper(this, imageUrls)
//                viewPager.setAdapter(adapter)
//
//                adapter.notifyDataSetChanged() //update the viewpager view with the new image
//            }
//        }


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


