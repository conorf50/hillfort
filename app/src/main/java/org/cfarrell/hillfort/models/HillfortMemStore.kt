package org.cfarrell.hillfort.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger {
  override fun findFavs(): List<HillfortModel> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  val hillforts = ArrayList<HillfortModel>()

  override fun findAll(): List<HillfortModel> {
    return hillforts
  }
  override fun delete(hillfort: HillfortModel) {
    hillforts.remove(hillfort)
  }
  override fun create(hillfort: HillfortModel) {
    hillfort.id = getId()
    hillforts.add(hillfort)
    logAll()
  }

  override fun update(hillfort: HillfortModel) {
    var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
    if (foundHillfort!= null) {
      foundHillfort.title = hillfort.title
      foundHillfort.description = hillfort.description
      foundHillfort.image = hillfort.image
      foundHillfort.lat = hillfort.lat
      foundHillfort.lng = hillfort.lng
      foundHillfort.zoom = hillfort.zoom
      foundHillfort.visitedFlag = hillfort.visitedFlag
      foundHillfort.favouriteFlag = hillfort.favouriteFlag
      foundHillfort.rating = hillfort.rating

      logAll();
    }
  }

  fun logAll() {
    hillforts.forEach { info("${it}") }
  }

  override fun findById(id:Long) : HillfortModel? {
    val foundPlacemark: HillfortModel? = hillforts.find { it.id == id }
    return foundPlacemark
  }


}