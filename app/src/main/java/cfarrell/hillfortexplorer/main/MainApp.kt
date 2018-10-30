package cfarrell.hillfortexplorer.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import cfarrell.hillfortexplorer.models.HillfortJSONStore
//import cfarrell.hillfortexplorer.models.HillfortMemStore
import cfarrell.hillfortexplorer.models.HillfortStore

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        //placemarks = PlacemarkMemStore()
        hillforts = HillfortJSONStore(applicationContext)
        info("HIllfort started")
    }
}