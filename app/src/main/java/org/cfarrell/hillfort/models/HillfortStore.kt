package org.cfarrell.hillfort.models

interface HillfortStore {
  fun findAll(): List<PlacemarkModel>
  fun create(placemark: PlacemarkModel)
  fun update(placemark: PlacemarkModel)
}