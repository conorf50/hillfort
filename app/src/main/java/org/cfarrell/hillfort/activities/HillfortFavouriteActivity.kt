package org.cfarrell.hillfort.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.cfarrell.hillfort.R
import org.cfarrell.hillfort.main.MainApp
import org.cfarrell.hillfort.models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast



class HillfortFavouriteActivity : AppCompatActivity(), HillfortListener, AnkoLogger {

    lateinit var app: MainApp
    // from here: https://www.techotopia.com/index.php/Kotlin_-_Making_Runtime_Permission_Requests_in_Android
    private val STORAGE_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_favourites)
        app = application as MainApp
        // set the title of the app bar
        super.setTitle(R.string.title_activity_favourite_hillforts)
        //toolbarMain.title = title
        //setSupportActionBar(toolbarMain)
        // from above link on requesting permissions
        setupPermissions()


        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HillfortAdapter(app.hillforts.findFavs(), this)

        loadHillforts ()
    }

    private fun loadHillforts() {
        showHillforts(app.hillforts.findFavs())
    }


    private fun makeRequest() {
        // make a permissions request, see here for source:
        //https://www.techotopia.com/index.php/Kotlin_-_Making_Runtime_Permission_Requests_in_Android
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_REQUEST_CODE)
    }



    private fun setupPermissions() {
        //source : https://www.techotopia.com/index.php/Kotlin_-_Making_Runtime_Permission_Requests_in_Android
        // the permission is changed from audio to read ext storage
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            toast("Please grant permission so images can be read.")
            // prompt the user to grant permission
            makeRequest()
        }
    }
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        // create the main landing page for the app
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    // override this function as specified in the permissions tutorial above
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            STORAGE_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    toast("Images will not load without granting permission")
                } else {
                    //toast("Permission")

                }
            }
        }
    }



    fun showHillforts(hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }


    override fun onHillfortClick(hillfort: HillfortModel) {

        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort edit", hillfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }
}