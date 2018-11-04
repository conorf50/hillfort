package org.cfarrell.hillfort

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.app_bar_navbar.*

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.cfarrell.hillfort.activities.HillfortListener
import org.cfarrell.hillfort.main.MainApp
import org.cfarrell.hillfort.models.HillfortModel
import org.cfarrell.hillfort.activities.HillfortActivity

import android.view.MenuItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_navbar.*
import org.cfarrell.hillfort.activities.HillfortAdapter

class HillfortListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, HillfortListener {
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp

        //setSupportActionBar(toolbar)
        val fab = findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener { view ->
            startActivityForResult<HillfortActivity>(0)

        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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


    override fun onHillfortClick(hillfort: HillfortModel) {

        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort edit", hillfort), 0)
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_hillforts -> {
                //goto main
            }
            R.id.nav_stats -> {
                // Handle the camera action
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
