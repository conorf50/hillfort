package cfarrell.hillfortexplorer.main

import android.app.Application
import cfarrell.hillfortexplorer.models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {
    val hillforts= ArrayList<HillfortModel>()


    override fun onCreate() {
        super.onCreate()
        info("HillfortExplorer started")
    }
}