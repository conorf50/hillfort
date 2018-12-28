package org.cfarrell.hillfort.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.card_hillfort.*
import org.cfarrell.hillfort.HillfortMapsActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.cfarrell.hillfort.R
import org.cfarrell.hillfort.main.MainApp
import org.cfarrell.hillfort.models.HillfortModel
import org.jetbrains.anko.toast


class HillfortListActivity : AppCompatActivity(), HillfortListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp
        //toolbarMain.title = title
        //setSupportActionBar(toolbarMain)


        // add a floating action button listener
        val fab: View = findViewById(R.id.fab_add)
        fab.setOnClickListener { view ->
            startActivityForResult<HillfortActivity>(0)

        }


        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HillfortAdapter(app.hillforts.findAll(), this)
        loadHillforts ()
    }

    private fun loadHillforts() {
        showHillforts(app.hillforts.findAll())
    }

    fun showHillforts(hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // create the main landing page for the app
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.mapView -> {
                startActivityForResult<HillfortMapsActivity>(0)
            }
//            R.id.settings -> {
//                startActivityForResult<HillfortSettingsActivity>(0)
//            }
//            R.id.favourites -> {
//                startActivityForResult<FavouriteHillfortsActivity>(0)
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHillfortClick(hillfort: HillfortModel) {

        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort edit", hillfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }
}