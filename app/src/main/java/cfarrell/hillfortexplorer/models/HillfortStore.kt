package cfarrell.hillfortexplorer.models

interface HillfortStore {
  fun findAll(): List<HillfortModel>
  fun create(placemark: HillfortModel)
  fun update(placemark: HillfortModel)
  fun delete(placemark: HillfortModel)
}