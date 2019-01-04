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
import kotlinx.android.synthetic.main.content_hillfort_maps.*
import org.cfarrell.hillfort.helpers.ImageViewPagerHelper
import java.time.Duration
import java.util.*


class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var presenter: HillfortPresenter
    lateinit var app: MainApp


    private val imageUrls = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
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
        presenter = HillfortPresenter(this)

        info("Hillfort Activity started..")


        btnAdd.setOnClickListener {
            if (hillfortTitle.text.toString().isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {
                presenter.doAddOrSave(hillfortTitle.text.toString(), hillfortDescription.text.toString(), Date(), checkBoxHillfortVisited.isChecked, checkBoxHillfortVisited.isChecked,hillfortRating.getRating().toInt())
            }
        }




        // listener for the deleteImage button

//        buttonDeleteImage.setOnClickListener { view ->
//
//            val viewPager = findViewById<ViewPager>(R.id.view_pager)
//
//            //toast("deleting image at" + viewPager.currentItem)
//            // hillfort image is currently null
//
//        }

        chooseImage.setOnClickListener {
            // this is a check to limit the amount of images a user can add
            if (imageUrls.size <= 4) {
                presenter.doSelectImage()
            } else {
                toast("Max amount of images selected")
            }

        }


        hillfortLocation.setOnClickListener {
            presenter.doSetLocation()
        }

    }



    fun showHillfort(hillfort: HillfortModel) {
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val checkboxVisit = findViewById<CheckBox>(R.id.checkBoxHillfortVisited)
        val checkboxFav = findViewById<CheckBox>(R.id.checkBoxHillfortFav)
        val hillfortRatingBar = findViewById<RatingBar>(R.id.hillfortRating)


        hillfortTitle.setText(hillfort.title)
        hillfortDescription.setText(hillfort.description)
        checkboxVisit.setChecked(hillfort.visitedFlag)
        checkboxFav.setChecked(hillfort.favouriteFlag)
        hillfortRatingBar.rating = hillfort.rating.toFloat()
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





    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
       menu.getItem(0).setVisible(true)
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
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }


}

