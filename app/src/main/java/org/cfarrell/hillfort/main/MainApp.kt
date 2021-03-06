package org.cfarrell.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.cfarrell.hillfort.models.HillfortJSONStore
import org.cfarrell.hillfort.models.HillfortStore

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts: HillfortStore

  override fun onCreate() {
    super.onCreate()
    hillforts = HillfortJSONStore(applicationContext)
    info("Hillfort started")
  }
}